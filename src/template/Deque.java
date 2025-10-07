package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;
import br.com.davidbuzatto.jsge.math.Vector2;
/**
 *
 * @author BV3043185
 */
public class Deque extends EngineFrame{
    
    private int[] elementosDeque;
    
    private int tamanho;
    private int inicio;
    private int fim;
    private int indiceUltimo;
    private int tamanhoFontBase;
    private int tamanhoFontMin;
    
    private Rectangle contornoFila;
    
    private GuiInputDialog inputTamanho;
    private GuiInputDialog inputAddFirst;
    private GuiInputDialog inputAddLast;
    
    private GuiButton mudarTamanho;
    private GuiButton addFirst;
    private GuiButton addLast;
    private GuiButton removeFirst;
    private GuiButton removeLast;
    private GuiButton clear;
    
    int x;
    int y;
    
    
    public Deque(){
        super(
            800,                 // largura                      / width
            600,                 // algura                       / height
            "Deque",             // título                       / title
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
        indiceUltimo = 0;
        tamanho = 10; //valor padrao, pode ser mudado
        inicio = 0;
        fim = 0;
        tamanhoFontBase = 30;
        tamanhoFontMin = 8;
        
        elementosDeque = new int[tamanho];
        
        contornoFila = new Rectangle(x, y, 400, 70);
        
        addFirst = new GuiButton(x, y - 100, 100, 50, "add First", this);
        removeFirst = new GuiButton(x, y - 40, 100, 30, "remove First", this);
        addLast = new GuiButton(x + 300, y - 100, 100, 50, "add Last", this);
        removeLast = new GuiButton(x + 300, y - 40, 100, 30, "remove Last", this);
        mudarTamanho = new GuiButton(x + 150, y - 40, 100, 30, "Mudar tamanho", this);
        clear = new GuiButton(x + 150 , y - 100 , 100, 50, "Clear", this);
        
        inputAddFirst = new GuiInputDialog("Inserir dados",
                "Insira um elemento no inicio",
                "Cancelar", true, this);
        
        inputAddLast = new GuiInputDialog("Inserir dados", 
                "Insira um elemento no final", 
                "Cancelar", true, this);
        
        
        inputTamanho = new GuiInputDialog("Inserir tamanho", 
                "Insira o tamanho da fila",
                "Cancelar", true, this);
        
    }
    
    @Override
    public void update( double delta ){
        addFirst.update(delta);
        removeFirst.update(delta);
        addLast.update(delta);
        removeLast.update(delta);
        mudarTamanho.update(delta);
        clear.update(delta);
        inputTamanho.update(delta);
        inputAddFirst.update(delta);
        inputAddLast.update(delta);
        
        if(addFirst.isMousePressed()){
            inputAddFirst.show(280, 100);
        }
        
        if(addLast.isMousePressed()){
            inputAddLast.show(280, 100);
        }
        
        if ( inputAddFirst.isCloseButtonPressed() || inputAddFirst.isCancelButtonPressed() ) {
            inputAddFirst.hide();
        }
        
        if(inputAddLast.isCloseButtonPressed() || inputAddLast.isCancelButtonPressed()){
            inputAddLast.hide();
        }

        //Add Last
        if(inputAddLast.isOkButtonPressed() || inputAddLast.isEnterKeyPressed()){
            if(ehInt(inputAddLast.getValue())){
                elementosDeque[indiceUltimo] = Integer.parseInt(inputAddLast.getValue());
                
                inicio = elementosDeque[0];
                fim = elementosDeque[indiceUltimo];
                inputAddLast.hide();
                indiceUltimo++;
                
            }else{
                inputAddLast.setText("Precisa ser um numero");
            }
        }
        
        //Add First
        if(inputAddFirst.isOkButtonPressed() || inputAddFirst.isEnterKeyPressed()){
            if(ehInt(inputAddFirst.getValue())){
                
                if(indiceUltimo < tamanho){
                    
                    for(int i = indiceUltimo; i >= 0; i--){
                        elementosDeque[i + 1] = elementosDeque[i];
                    }
                    
                    elementosDeque[0] = Integer.parseInt(inputAddFirst.getValue());                  
                    inicio = elementosDeque[0];
                    fim = elementosDeque[indiceUltimo];
                    indiceUltimo++;
                    inputAddFirst.hide();
                    
                }
            } else{
                inputAddLast.setText("Precisa ser um numero");
            }
        }
        
        //Remove First
        if(removeFirst.isMousePressed()){
            for(int i = 0; i < tamanho - 1; i++){
                elementosDeque[i] = elementosDeque[i + 1]; 
            }
            
            if(indiceUltimo > 0){
                indiceUltimo--;
            }
            
            inicio = elementosDeque[0];
            fim = elementosDeque[indiceUltimo];
        }
        
        //Remove Last
        if(removeLast.isMousePressed()){
            if(indiceUltimo > 0){
                indiceUltimo--;
                fim = elementosDeque[indiceUltimo];
            }else{
                inicio = elementosDeque[0];
                
            }
        }
        
        //clear
        if(clear.isMousePressed()){
            elementosDeque = new int[tamanho];
            indiceUltimo = 0;
            
            //precisa repetir esse codigo p/ atualizar o inicio;
            inicio = elementosDeque[0];
            fim = elementosDeque[0];
        }
        
        if(mudarTamanho.isMousePressed()){
            inputTamanho.show();
        }
        
        //inputTamanho
        if(inputTamanho.isOkButtonPressed() || inputTamanho.isEnterKeyPressed()){
            tamanho = Integer.parseInt(inputTamanho.getValue());
            
            if(ehInt(inputTamanho.getValue())){
                
                indiceUltimo = 0;
                inputTamanho.hide();
                elementosDeque = new int[Integer.parseInt(inputTamanho.getValue())];
                             
            }else{
                inputTamanho.setText("Precisa ser um numero");
            }
        }
        
        if(inputTamanho.isCloseButtonPressed()){
            inputTamanho.hide();
        }
        
        
    }
    
    @Override
    public void draw(){
        desenharDeque(contornoFila, elementosDeque);
        addFirst.draw();
        removeFirst.draw();
        addLast.draw();
        removeLast.draw();
        clear.draw();
        mudarTamanho.draw();
        inputTamanho.draw();
        inputAddFirst.draw();
        inputAddLast.draw();
        
        drawText("Inicio: " + inicio + "\n fim: " + fim,
                200, 300, BLACK);
     
    }
    
    public void desenharDeque(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = array.length;
        int tamanhoFont = Math.max(tamanhoFontBase - array.length,  tamanhoFontMin);
        
        for(int i = 0; i < indiceUltimo; i++){
            double yPos = contorno.y;
            double xPos = contorno.x + (contorno.width / tamanhoElemento) * i;
            
            //desenhar o elemento
            drawText(Integer.toString(elementosDeque[i]), 
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
        new Deque();
    }
}
