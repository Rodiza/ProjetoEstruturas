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
    private int inicio;
    private int contador;
    
    private Rectangle contornoFila;
    
    private GuiInputDialog inputTamanho;
    private GuiInputDialog inputEnqueue;
    private GuiButton mudarTamanho;
    private GuiButton enqueue;
    private GuiButton dequeue;
    
    int x;
    int y;
    
    
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
        x = 200;
        y = 400;
        contador = 0;
        elementosFila = new int[10];
        
        contornoFila = new Rectangle(x, y, 400, 70);
        enqueue = new GuiButton(x + 40, y - 200, 100, 70, "ENQUEUE", this);
        dequeue = new GuiButton(x + 260, y - 200, 100, 70, "DEQUEUE", this);
        mudarTamanho = new GuiButton(x + 40, y - 120, 100, 40, "Mudar tamanho", this);
        
        inputEnqueue = new GuiInputDialog("Inserir dados",
                "Insira um elemento na fila",
                "Cancelar", true, this);
        inputTamanho = new GuiInputDialog("Inserir tamanho", 
                "Insira o tamanho da fila",
                "Cancelar", false, this);
    }
    
    @Override
    public void update( double delta ){
        //inputTamanho.show();
        enqueue.update(delta);
        dequeue.update(delta);
        mudarTamanho.update(delta);
        inputTamanho.update(delta);
        inputEnqueue.update(delta);
        
        if(enqueue.isMousePressed()){
            inputEnqueue.show();
        }
        
        if ( inputEnqueue.isCloseButtonPressed() ) {
            inputEnqueue.hide();
        }
        
        //Enqueue
        if(inputEnqueue.isOkButtonPressed() || inputEnqueue.isEnterKeyPressed()){
            if(ehInt(inputEnqueue.getValue())){
                elementosFila[contador] = Integer.parseInt(inputEnqueue.getValue());
                
                inicio = elementosFila[contador];
                inputEnqueue.hide();
                contador++;
                
            }else{
                inputEnqueue.setText("Precisa ser um numero");
            }
        }
    
        
    }
    
    @Override
    public void draw(){
        desenharFila(contornoFila, elementosFila);
        enqueue.draw();
        dequeue.draw();
        mudarTamanho.draw();
        //inputTamanho.draw();
        inputEnqueue.draw();
        
        
     
    }
    
    public void desenharFila(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = array.length;
        
        for(int i = 0; i < contador; i++){
            double yPos = contorno.y + contorno.height - (i * tamanhoElemento) - tamanhoElemento;
            double xPos = contorno.x + (contorno.width / 2);
            
            //desenhar o elemento
            drawText(Integer.toString(elementosFila[i]), 
                    new Vector2(xPos - 10, yPos + 5),
                    30 - array.length,
                    BLACK
                    );
            
            //desenha o contorno do elemento
            drawRectangle(new Rectangle(contorno.x, 
                    yPos,
                    100, tamanhoElemento), BLACK);
        
        }
    }
    
    private boolean ehInt(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void main( String[] args ) {
        new Fila();
    }
}
