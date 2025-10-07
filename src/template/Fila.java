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
    private int tamanho;
    private int inicio;
    private int contador;
    private int tamanhoFontBase;
    private int tamanhoFontMin;
    
    private Rectangle contornoFila;
    
    private GuiInputDialog inputTamanho;
    private GuiInputDialog inputEnqueue;
    private GuiButton mudarTamanho;
    private GuiButton enqueue;
    private GuiButton dequeue;
    private GuiButton clear;
    
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
        tamanho = 10; //valor padrao, pode ser mudado
        tamanhoFontBase = 30;
        tamanhoFontMin = 8;
        
        elementosFila = new int[tamanho];
        
        contornoFila = new Rectangle(x, y, 400, 70);
        
        enqueue = new GuiButton(x + 40, y - 200, 100, 70, "ENQUEUE", this);
        dequeue = new GuiButton(x + 260, y - 200, 100, 70, "DEQUEUE", this);
        mudarTamanho = new GuiButton(x + 40, y - 120, 100, 40, "Mudar tamanho", this);
        clear = new GuiButton(x + 260 , y - 120 , 100, 40, "Clear", this);
        
        inputEnqueue = new GuiInputDialog("Inserir dados",
                "Insira um elemento na fila",
                "Cancelar", true, this);
        inputTamanho = new GuiInputDialog("Inserir tamanho", 
                "Insira o tamanho da fila",
                "Cancelar", true, this);
    }
    
    @Override
    public void update( double delta ){
        enqueue.update(delta);
        dequeue.update(delta);
        mudarTamanho.update(delta);
        clear.update(delta);
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
                
                inicio = elementosFila[0];
                inputEnqueue.hide();
                contador++;
                
            }else{
                inputEnqueue.setTitleBarBackgroundColor(RED);
                inputEnqueue.setText("Precisa ser um numero");
            }
        }
        
        //Dequeue
        if(dequeue.isMousePressed()){
            for(int i = 0; i < tamanho - 1; i++){
                elementosFila[i] = elementosFila[i + 1]; 
            }
            
            if(contador > 0){
                contador--;
            }
            
            inicio = elementosFila[0];
        }
        
        //clear
        if(clear.isMousePressed()){
            elementosFila = new int[tamanho];
            contador = 0;
            
            //precisa repetir esse codigo p/ atualizar o inicio;
            inicio = elementosFila[0];
        }
        
        if(mudarTamanho.isMousePressed()){
            inputTamanho.show();
        }
        
        //inputTamanho
        if(inputTamanho.isOkButtonPressed() || inputTamanho.isEnterKeyPressed()){
            tamanho = Integer.parseInt(inputTamanho.getValue());
            
            if(ehInt(inputTamanho.getValue())){
                
                contador = 0;
                inputTamanho.hide();
                elementosFila = new int[Integer.parseInt(inputTamanho.getValue())];
                             
            }else{
                inputTamanho.setTitleBarBackgroundColor(RED);
                inputTamanho.setText("Precisa ser um numero");
            }
        }
        
        if(inputTamanho.isCloseButtonPressed()){
            inputTamanho.hide();
        }      
        
        
    }
    
    @Override
    public void draw(){
        desenharFila(contornoFila, elementosFila);
        enqueue.draw();
        dequeue.draw();
        clear.draw();
        mudarTamanho.draw();
        inputTamanho.draw();
        inputEnqueue.draw();
        
        drawText("Início: " + inicio, 330, 500, 30, BLACK);
     
    }
    
    public void desenharFila(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = array.length;
        int tamanhoFont = Math.max(tamanhoFontBase - array.length,  tamanhoFontMin);
        
        for(int i = 0; i < contador; i++){
            double yPos = contorno.y;
            double xPos = contorno.x + (contorno.width / tamanhoElemento) * i;
            
            //desenhar o elemento
            drawText(Integer.toString(elementosFila[i]), 
                    new Vector2(xPos + 10, yPos + 10),
                    tamanhoFont,
                    BLACK);
            
            //desenha o contorno do elemento
            drawRectangle(new Rectangle(xPos, 
                    yPos,
                    contorno.width / tamanhoElemento, 70), BLACK);
        
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
