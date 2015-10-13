package com.aziis98.testing;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.primitives.*;

import javax.imageio.*;
import java.io.*;

import static java.awt.Color.*;

public class TestCanvas {

    public static void main(String[] args) throws IOException {

        Canvas canvas = new Canvas();

        canvas.traslateBy( 300, 100 );

        PPoint p1 = new PPoint(200, 200);
        PPoint p2 = new PPoint(600, 460);

        PPoint p3 = new PPoint(800, 300);
        PPoint p4 = new PPoint(180, 400);

        PLine l1 = new PLine( p1, p2 );
        PLine l2 = new PLine( p3, p4 );

        PPoint p5 = l1.intersect( l2 );
        PPoint p6 = PPoint.centroid( p1, p4, p5 );

        PLine l3 = l1.perpendicular( p6 );

        PPoint p7 = PPoint.orthocenter( p1, p2, p3 );


        canvas.setBackground( WHITE );

        canvas.setColor( RED );
        canvas.draw( l1 );
        canvas.draw( l2 );
        canvas.draw( l3 );

        canvas.setColor( BLACK );
        canvas.draw( p1 );
        canvas.draw( p2 );
        canvas.draw( p3 );
        canvas.draw( p4 );

        canvas.setColor( BLUE );
        canvas.draw( p5 );
        canvas.draw( p6 );

        ImageIO.write( canvas.renderImage( 1920, 1080 ), "png", new File( "res/TestResult.png" ) );

    }

}
