package com.aziis98.vrenderer.api;

import com.aziis98.vrenderer.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.util.function.*;

public class Canvas {

    private LinkedList<ICanvasPainter> paintStack = new LinkedList<>();

    public BufferedImage renderImage(int width, int height) {
        Config config = new Config();
        config.width = width;
        config.height = height;
        return renderImage( config );
    }

    private BufferedImage renderImage(Config config) {
        // Initial Config
        BufferedImage bufferedImage = new BufferedImage( config.width, config.height, BufferedImage.TYPE_INT_ARGB );

        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        {
            graphics2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                    config.antialiasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF );
        }


        // Renderer
        for (ICanvasPainter painter : paintStack)
        {
            painter.draw( graphics2D, config );
        }

        return bufferedImage;
    }

    public void draw(Consumer<Graphics2D> simplePainter) {
        draw( (g, config) -> simplePainter.accept( g ) );
    }

    public void draw(ICanvasPainter iCanvasPainter) {
        paintStack.add( iCanvasPainter );
    }

    public void draw(ICanvasPainter... iCanvasPainter) {
        for (ICanvasPainter painter : iCanvasPainter)
        {
            draw( painter );
        }
    }

    //region Base Functions

    public void setColor(Color color) {
        draw( graphics2D -> graphics2D.setColor( color ) );
    }

    public void setColor(int color) {
        draw( graphics2D -> graphics2D.setColor( Utils.decodeColor( color ) ) );
    }

    public void setBackground(Color color) {
        draw( (g, config) -> {
            g.setBackground( color );
            g.clearRect( 0, 0, config.width, config.height );
        } );
    }

    public void traslateToCenter() {
        draw( (g, config) -> g.setTransform( AffineTransform.getTranslateInstance( config.width / 2, config.height / 2 ) ) );
    }

    public void traslateBy(int dx, int dy) {
        draw( (g, config) -> g.translate( dx, dy ) );
    }

    //endregion

    public static class Config {
        public int width, height;
        public boolean antialiasing = true;
    }
}
