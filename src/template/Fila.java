package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
/**
 *
 * @author BV3043185
 */
public class Fila extends EngineFrame{
    
    private int[] elementosFila;
    private Rectangle contornoFila;
    
    
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
    
    public void desenharFila(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = contorno.height / elementosPilha.length;
        
        for(int i = 0; i < contador; i++){
            //Começar desenhando da base da pilha
            double yPos = contorno.y + contorno.height - (i * tamanhoElemento) - tamanhoElemento;
            double xPos = contorno.x + (contornoPilha.width / 2);
            
            //desenha o numero
            drawText(Integer.toString(elementosPilha[i]), 
                    new Vector2(xPos - 10, yPos + 10),
                    20,
                    BLACK
                    );
            
            //desenha o contorno da pilha
            drawRectangle(new Rectangle(contorno.x, 
                    yPos,
                    100, tamanhoElemento), BLACK);
        }
    
    
    public static void main( String[] args ) {
        new Fila();
    }
}
