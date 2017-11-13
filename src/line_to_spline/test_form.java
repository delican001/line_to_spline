package line_to_spline;


import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by delic on 28.10.2017.
 */
public class test_form {

    public static void main (String[] agrs) throws IOException, SAXException, ParserConfigurationException {
        File file = new File("C:\\Users\\delic\\Downloads\\map_new.osm");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document =  builder.parse(file);
        org.w3c.dom.Element root = document.getDocumentElement();
        double minlat = Double.parseDouble(root.getElementsByTagName("bounds").item(0).getAttributes().getNamedItem("minlat").getNodeValue());
        double minlon = Double.parseDouble(root.getElementsByTagName("bounds").item(0).getAttributes().getNamedItem("minlon").getNodeValue());
        int length = root.getElementsByTagName("node").getLength();


        int new_length = root.getElementsByTagName("way").getLength();
        System.out.println(new_length);
        ArrayList<ArrayList<Double>> way_x = new ArrayList<>();
        ArrayList<ArrayList<Double>> way_y = new ArrayList<>();

        for (int i=0;i<new_length;i++)
        {
            ArrayList <Double> coords_x = new ArrayList<>();
            ArrayList <Double> coords_y = new ArrayList<>();
            NodeList ndlist = root.getElementsByTagName("way").item(i).getChildNodes();
            //System.out.println(ndlist.item(0).getNodeName());
            for (int j=0;j<ndlist.getLength();j++)
                if (ndlist.item(j).getNodeName()=="nd") {
                    String nd_id = ndlist.item(j).getAttributes().getNamedItem("ref").getNodeValue();
                   // System.out.println(nd_id);
                    for (int k=0;k<root.getElementsByTagName("node").getLength();k++)
                    { //  System.out.println(root.getElementsByTagName("node").item(k).getAttributes().getNamedItem("id").getNodeValue());
                        if (root.getElementsByTagName("node").item(k).getAttributes().getNamedItem("id").getNodeValue().equals(nd_id))
                        {
                            coords_x.add(Double.parseDouble(root.getElementsByTagName("node").item(k).getAttributes().getNamedItem("lat").getNodeValue()));
                            coords_y.add(Double.parseDouble(root.getElementsByTagName("node").item(k).getAttributes().getNamedItem("lon").getNodeValue()));
                        }
                    }
                }
                if (coords_x.size()>19 && coords_x.size()<23) {
                System.out.println(root.getElementsByTagName("way").item(i).getAttributes().getNamedItem("id"));
                String tst = root.getElementsByTagName("way").item(i).getAttributes().getNamedItem("id").getNodeValue().toString();
                    if (root.getElementsByTagName("way").item(i).getAttributes().getNamedItem("id").getNodeValue().toString().equals("23412766")) {
                        way_x.add(coords_x);
                        way_y.add(coords_y);
                    }

            }
            System.out.println("______");
        }

        for (int i=0;i<way_x.size();i++)
        {
            for (int j=0;j<way_x.get(i).size();j++)
            {
                double[] temp = ll_to_xy.lat_long_to_xy(way_x.get(i).get(j),way_y.get(i).get(j),minlat,minlon);
                way_x.get(i).set(j,temp[0]);
                way_y.get(i).set(j,temp[1]);
            }
        }
       /* double[] coord_x = new double[coords_x.size()];
        double[] coord_y = new double[coords_x.size()];
        for (int i=0;i<coords_x.size();i++)
        {
//            Node message_lat = root.getElementsByTagName("node").item(i).getAttributes().getNamedItem("lat");
  //          Node message_lon = root.getElementsByTagName("node").item(i).getAttributes().getNamedItem("lon");
    //        double lat = Double.parseDouble(message_lat.getNodeValue());
      //      double lon = Double.parseDouble(message_lon.getNodeValue());
            double[] new_coords = ll_to_xy.lat_long_to_xy(coords_x.get(i),coords_y.get(i),minlat,minlon);
            coord_x[i]=new_coords[0];
            coord_y[i]=new_coords[1];

        }

        Sort sort = new Sort(coords_x,coords_y);
        //double[][] sorted = sort.get_sorted();
        double[][] sorted = new double[3][coords_x.size()];
        for (int i=0;i<coords_x.size();i++)
        {
            sorted[0][i]=coord_x[i];
            sorted[1][i]=coord_y[i];
            sorted[2][i]=-1;
        }
        sorted=smoothing.go(sorted);

        for (int i=0;i<sorted[0].length;i++)
        {
            System.out.println("x="+sorted[0][i]+" y="+sorted[1][i]+"     "+sorted[2][i]);
            System.out.println();
        }
*/
        ArrayList <ArrayList<Integer>> colors = smoothing.go(way_x,way_y);
        for (int i=0;i<colors.size();i++)
            for (int j=0;j<colors.get(i).size();j++)
            {
                System.out.println("color="+colors.get(i).get(j));
            }


        JFrame jFrame = new JFrame() {
            {
                setBounds(0, 0,400,500);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        };
  //      double[][] finalSorted = sorted;
        JPanel contentPane = new JPanel(){
            Graphics2D g2;

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                double scl = 0.5;
                for (int j = 0; j < way_x.size(); j++) {
                    //int j = 2;
                    g.drawOval((int) (double) (way_x.get(j).get(0) / scl), (int) (double) (way_y.get(j).get(0) / scl), 15, 15);
                    for (int i = 0; i < way_x.get(j).size() - 1; i++) {
                        g.drawOval((int) (double) (way_x.get(j).get(i + 1) / scl), (int) (double) (way_y.get(j).get(i + 1) / scl), 5, 5);
                        g2.drawLine((int) (double) (way_x.get(j).get(i) / scl), (int) (double) (way_y.get(j).get(i) / scl), (int) (double) (way_x.get(j).get(i + 1) / scl), (int) (double) (way_y.get(j).get(i + 1) / scl));
                        //g.drawOval((int)finalSorted[0][i]/5,(int)finalSorted[1][i]/2,5,5);
                        //g.drawPolygon(coord_x,coord_y,length);
                        //g.drawPolyline(coord_x,coord_y,length);
                        //g2.drawLine(coord_x[i],coord_y[i],coord_x[i+1],coord_y[i+1]);
                        //g2.drawLine((int) finalSorted[0][i],(int) finalSorted[1][i],(int) finalSorted[0][i+1],(int) finalSorted[1][i+1]);

                    }
                }
            }
                //System.out.println(finalSorted[0].length);



        };
        jFrame.setContentPane(contentPane);
        

}
}
