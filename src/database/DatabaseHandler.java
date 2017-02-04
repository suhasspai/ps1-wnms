package database;

import static com.mongodb.client.model.Filters.eq;
import static SwingUtilities.SwingConsole.run2;

import SwingUtilities.NodeDataTable;
import application.SwingView;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import packets.AggregatedPacket;
import packets.SingleNodePacket;
import serial.DecodeSerialData;

/**
 *
 * @author Suhas S Pai
 */

public class DatabaseHandler {
    public static void setup(List<Byte> list) {
	try {
            open();
            array=new byte[list.size()];
            for (int i=0; i<list.size(); i++)
                array[i]=list.get(i);
            DecodeSerialData DSD=new DecodeSerialData(array);
            if (DSD.isSingle())
                insertSingleNode(DSD.decodeSinglePacket());
            else
                insertAggregatedNode(DSD.decodeAggregatedPacket());
        } finally {
            mongoClient.close();
        }
    }
    public static void setupBad(ArrayList<Byte> list) {
        try {
            open();
            Byte[] a={};
            db.getCollection("Others").insertOne(
                new Document("Packet", getPacketSize(list.size())+bytesToHex(list.toArray(a)))
            );
        } finally {
            mongoClient.close();
        }
    }
    public static void setupBroken(ArrayList<Byte> list) {
        try {
            open();
            Byte[] a={};
            db.getCollection("Others").insertOne(
                new Document("Packet", bytesToHex(list.toArray(a)))
            );
        } finally {
            mongoClient.close();
        }
    }
    public static void open() {
        mongoClient=new MongoClient();
	Calendar c=Calendar.getInstance(TimeZone.getTimeZone("IST"));
	Integer date=c.get(Calendar.DAY_OF_MONTH), month=c.get(Calendar.MONTH),
            year=c.get(Calendar.YEAR);
	dbName=giveMonth(month)+"-"+date.toString() +"-"+year.toString();
	db = mongoClient.getDatabase(dbName);
	//db.getCollection("SingleNodes").drop();
        //db.getCollection("ClusterHeads").drop();
        //db.getCollection("Others").drop();
    }
    public static void query(Integer nodeNo) {
        querySingleNodes(nodeNo);
        queryClusterHeads(nodeNo);
    }
  
    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static String dbName;
    private static byte[] array;
    private static final String MAP="function map() {" +
                                        "for(var i in this.Nodes) {" +
                                            "emit({" +
                                                    "\"NodeID\": this.Nodes[i].NodeID," +
                                                    "\"Timestamp\": this.Timestamp" +
                                            "}," +
                                            "{" +
                                                "\"Data\": this.Nodes[i].Data," +
                                                "\"Health\": this.Nodes[i].Health," +
                                                "\"GroupID\": this.GroupID" +
                                            "});" +
                                        "}" +
                                    "}",
            RED="function reduce(key, value) {" +
                    "return value;" +
                "}";
    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String giveMonth(int month) {
        switch (month) {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
		return "Sep";
            case 9:
		return "Oct";
            case 10:
		return "Nov";
            case 11:
		return "Dec";
            default:
		return null;
        }
    }
    private static void insertSingleNode(SingleNodePacket SNP) {
        Document singleNodeDocument=new Document("GroupID", SNP.getGroupID())
            .append("NodeID", SNP.getNodeID())
            .append("No_of_sensor_values", SNP.getNo_of_sensor_values());
        for (int i=0; i<SNP.getNo_of_sensor_values(); i++)
            singleNodeDocument.append(SNP.getMeasurementBlock().getSensorDataBlock(i)
                .getSensorType(), SNP.getMeasurementBlock().getSensorDataBlock(i)
                .getValue());
        singleNodeDocument.append("Health", SNP.getHealth());
        singleNodeDocument.append("Timestamp", SNP.getTime());
        db.getCollection("SingleNodes").insertOne(singleNodeDocument);
        if (NodeDataTable.isWindowActive()) {
            if (Objects.equals(SNP.getNodeID(), NodeDataTable.getActiveNodeNo()))
                displayInWindow(singleNodeDocument);
        }
        SwingView.changeIcon(((Document)singleNodeDocument.get("Health")).get("Alarm")=="true",
            ((Document)singleNodeDocument.get("NodeID")).toString());
    }
    private static void insertAggregatedNode(AggregatedPacket AP) {
        Document aggregatedDocument=new Document("GroupID", AP.getGroupID())
                                        .append("NodeID", AP.getNodeID())
                                        .append("No_of_nodes", AP.getNo_of_nodes())
                                        .append("Nodes", AP.getNodesAsDocument())
                                        .append("Health", AP.getHealth())
                                        .append("Timestamp", AP.getTime());
        db.getCollection("ClusterHeads").insertOne(aggregatedDocument);
        if (NodeDataTable.isWindowActive()) {
            if (Objects.equals(AP.getNodeID(), NodeDataTable.getActiveNodeNo())) {
                Document newDoc=new Document("GroupID", AP.getGroupID())
                                        .append("NodeID", AP.getNodeID())
                                        .append("No_of_nodes", AP.getNo_of_nodes())
                                        .append("Health", AP.getHealth())
                                        .append("Timestamp", AP.getTime());
                displayInWindow(newDoc);
            } else {
                ArrayList<Document> listOfNodes=new ArrayList<>(AP.getNodesAsDocument());
                for (Document node : listOfNodes) {
                    if (Objects.equals((Integer)node.get("NodeID"), NodeDataTable.getActiveNodeNo())) {
                        displayInWindow(node);
                        break;
                    }
                }
            }
        }
    }
    private static String bytesToHex(Byte[] bytes) {
        char[] hexChars=new char[bytes.length*3];
        for (int j=0; j<bytes.length; j++) {
            int v=bytes[j]&0xFF;
            hexChars[j*3]=hexArray[v>>>4];
            hexChars[j*3+1]=hexArray[v&0x0F];
            hexChars[j*3+2]=' ';
        }
        return new String(hexChars);
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String getPacketSize(Integer size) {
        String sizeString="7E ";
        for (int i=3; i>=0; i--) {
            sizeString=sizeString.concat(decodeHexSize((int)(size/Math.pow(16, i))));
            size%=(int)Math.pow(16, i);
            if (i%2==0)
                sizeString=sizeString.concat(" ");
        }
        return sizeString;
    }
    private static String decodeHexSize(Integer size) {
        switch(size) {
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "A";
            case 11:
                return "B";
            case 12:
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15:
                return "F";
            default:
                return "";
        }
    }
    private static void queryClusterHeads(Integer nodeNo) {
        try {
            open();
            DB database=mongoClient.getDB(dbName);
            DBCollection collection=database.getCollection("ClusterHeads");
            MapReduceCommand mapReduceCommand = new MapReduceCommand(collection, MAP, RED, "mro",
                MapReduceCommand.OutputType.REPLACE, null);
            collection.mapReduce(mapReduceCommand);
            Block<Document> printResults=(final Document document)->{
                displayInWindow(document);
            };
            try {
                db.getCollection("mro").find(eq("_id.NodeID", nodeNo))
                    .sort(new Document("Timestamp", -1)).limit(1).forEach(printResults);
            } catch (NullPointerException e) {
                db.getCollection("mro").find().forEach(printResults);
            } catch (Exception e) {
                
            }
        } finally {
            mongoClient.close();
        }
    }
    private static void querySingleNodes(Integer nodeNo) {
        System.out.println(nodeNo);
        try {
            open();
            Block<Document> printResults=(final Document document)->{
                displayInWindow(document);
            };
            try {
                db.getCollection("SingleNodes").find(eq("NodeID", nodeNo))
                    .sort(new Document("Timestamp", -1)).limit(1).forEach(printResults);
            } catch (NullPointerException e) {
                db.getCollection("SingleNodes").find().forEach(printResults);
            } 
        } finally {
            mongoClient.close();
        }
    }
    private static void displayInWindow(Document data){
        if (!NodeDataTable.isWindowActive()) {
            NodeDataTable NDT=new NodeDataTable(data);
            NDT.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                    NodeDataTable.setWindowActive(true);
                }
                @Override
                public void windowClosing(WindowEvent e) {
                    NodeDataTable.setWindowActive(false);
                }
                @Override
                public void windowClosed(WindowEvent e) {}
                @Override
                public void windowIconified(WindowEvent e) {}
                @Override
                public void windowDeiconified(WindowEvent e) {}
                @Override
                public void windowActivated(WindowEvent e) {}
                @Override
                public void windowDeactivated(WindowEvent e) {}
            });
            run2(NDT, 800, 500);
        } else
            NodeDataTable.change(data);
    }
}