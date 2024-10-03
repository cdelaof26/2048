package ui;

import game.Board;
import game.GameStroke;
import game.Utilities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import javax.swing.Timer;
import utils.LibUtilities;


/**
 *
 * @author cristopher
 */
public class UIBoard extends Canvas implements ComponentSetup {
    private int width, height;
    private Board board;
    
    private boolean paintInstructions = true;
    private static final String DIFICULTY_SETTINGS = "Press [3-9] to change game dificulty";
    private static final String QUIT_TEXT = "Press " + Utilities.RESET_KEY + " to restart and " + Utilities.EXIT_KEY + " to quit";
    
    private static final String INSTRUCTIONS = "Press arrow keys or WASD to start";
    private boolean blinkPaint = true;
    private int blinkState = 0;
    
    private final Timer graphicsThread = new Timer(16, (Event) -> {
        render();
    });
    
    
    public UIBoard(int width, int height, int k) {
        this.width = width;
        this.height = height;
        this.board = new Board(k);
    }
    
    @Override
    public void initUI() {
        createBufferStrategy(2);
        graphicsThread.start();
    }
    
    @Override
    public void updateUISize() {
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void updateUIFont() { }

    @Override
    public void updateUITheme() { }

    @Override
    public void updateUIColors() { }

    @Override
    public void setUseAppTheme(boolean useAppTheme) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUseAppColor(boolean useAppColor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRoundCorners(boolean roundCorners) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPaintBorder(boolean paintBorder) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        width = preferredSize.width;
        height = preferredSize.height;
        
        preferredSize.width *= UIProperties.uiScale;
        preferredSize.height *= UIProperties.uiScale;
        
        super.setPreferredSize(preferredSize);
    }
    
    protected void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        
        Graphics2D g2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        renderBoard(g2D);
        renderGame(g2D);
        
        g2D.dispose();
        bufferStrategy.show();
    }
    
    protected void renderStrings(Graphics2D g2D, double margin) {
        String _score = board.getStringScore();
        Font scoreFont = UIProperties.APP_TITLE_FONT;
        
        g2D.setColor(UIProperties.APP_FG);
        g2D.setFont(scoreFont);
        g2D.drawString(_score, (int) margin, (int) margin - LibUtilities.getTextDimensions(_score, scoreFont).height / 2);
        
        
        Font instructionsFont = UIProperties.APP_FONT;
        g2D.setColor(UIProperties.DIM_TEXT_COLOR);
        g2D.setFont(instructionsFont);

        Dimension topRightText = LibUtilities.getTextDimensions(paintInstructions ? DIFICULTY_SETTINGS : QUIT_TEXT, instructionsFont);
        g2D.drawString(paintInstructions ? DIFICULTY_SETTINGS : QUIT_TEXT, width - (int) margin - topRightText.width, (int) margin - topRightText.height);
        
        if (paintInstructions) {
            blinkState++;
            if (blinkState == 50) {
                blinkState = 0;
                blinkPaint = !blinkPaint;
            }
            
            if (blinkPaint) {
                Dimension instructionsDimension = LibUtilities.getTextDimensions(INSTRUCTIONS, instructionsFont);
                g2D.drawString(INSTRUCTIONS, width / 2 - instructionsDimension.width / 2, height - instructionsDimension.height);
            }
        }
    }
    
    protected void paintTile(Graphics2D g2D, double x, double y, double length, int value) {
        int colorIndex = (int) (Math.log(value) / Math.log(2));
        Rectangle2D r = new Rectangle2D.Double(x, y, length, length);
        
        g2D.setColor(colorIndex < Utilities.AVAILABLE_COLORS ? Utilities.GAME_COLORS[colorIndex] : Color.BLACK);
        g2D.fill(r);
        
        String _value = "" + value;
        Font textFont;
        if (board.getBoardSize() < 7)
            textFont = _value.length() > 4 ? UIProperties.APP_SUBTITLE_FONT : UIProperties.APP_BOLD_TITLE_FONT;
        else
            textFont = _value.length() > 4 ? UIProperties.APP_FONT : UIProperties.APP_SUBTITLE_FONT;
        
        Dimension textDimension = LibUtilities.getTextDimensions(_value, textFont);
        float valueX = (float) (x + length / 2 - textDimension.width / 2);
        float valueY = (float) (y + length / 2 + textDimension.height / 3);
        
        g2D.setColor(UIProperties.DARK_UI_FG);
        g2D.setFont(textFont);
        g2D.drawString(_value, valueX, valueY);
    }
    
    protected void renderBoard(Graphics2D g2D) {
        double margin = width * 0.1;
        double boardLength = width - margin * 2;
        double cellLength = boardLength / board.getBoardSize();
        double boardEnd = margin + boardLength;
        
        
        g2D.setColor(UIProperties.APP_BG);
        g2D.fillRect(0, 0, width, height);
        
        renderStrings(g2D, margin);
        
        g2D.setColor(UIProperties.APP_BGA);
        g2D.fill(new Rectangle2D.Double(margin, margin, boardLength, boardLength));
        
        double _i, _j;
        
        g2D.setColor(UIProperties.DIM_TEXT_COLOR);
        for (int i = 0; i <= board.getBoardSize(); i++) {
            _i = margin + i * cellLength;
            g2D.draw(new Line2D.Double(_i, margin, _i, boardEnd));
            g2D.draw(new Line2D.Double(margin, _i, boardEnd, _i));
        }
        
        int value;
        for (int i = 0; i < board.getBoardSize(); i++)
            for (int j = 0; j < board.getBoardSize(); j++) {
                value = board.getData(i, j);
                if (value == 0)
                    continue;
                
                _i = margin + i * cellLength;
                _j = margin + j * cellLength;
                paintTile(g2D, _i, _j, cellLength, value);
            }
    }
    
    protected void renderGame(Graphics2D g2D) {
        
    }
    
    public void setBoardSize(int boardSize) {
        if (!paintInstructions)
            return;
        
        graphicsThread.stop();
        board = new Board(boardSize);
        graphicsThread.start();
    }
    
    public void performStroke(GameStroke stroke) {
        if (paintInstructions)
            paintInstructions = !paintInstructions;
        
        board.generateRandomTiles();
    }
}
