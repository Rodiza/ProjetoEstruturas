package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;
import br.com.davidbuzatto.jsge.math.Vector2;
/**
 *
 * @author BV3043185
 */
public class Fila extends EngineFrame{
    
    private int[] elementosFila;
    
    
    public Fila(){
        super(
            800,                 // largura                      / width
            600,                 // algura                       / height
            "Fila",             // título                       / title
            60,                  // quadros por segundo desejado / target FPS
            true,                // suavização                   / antialiasing
            false,               // redimensionável              / resizable
            false,               // tela cheia                   / full screen
            false,               // sem decoração                / undecorated
            false,               // sempre no topo               / always on top
            false                // fundo invisível              / invisible background
        );
    }
    
    @Override
    public void create() {
         
    }
    
    @Override
    public void update( double delta ){
        
    }
    
    @Override
    public void draw(){
        
    }
    
    
    public static void main( String[] args ) {
        new Fila();
    }
}
