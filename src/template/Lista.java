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
public class Lista extends EngineFrame{
    
    private int[] elementosLista;
    private boolean[] desenharOuNao;
    //desenharOuNao serve como um array paralelo ao elementosLista
    //que vai dizer se o elemento deve ser desenhado ou nao(duh)
    private boolean desenhar;
    
    private int tamanho;
    private int inicio;
    private int fim;
    private int indiceUltimo;
    private int tamanhoFontBase;
    private int tamanhoFontMin;
    
    private Rectangle contornoFila;
    
    private GuiInputDialog inputTamanho;
    private GuiInputDialog inputAdd;
    
    private GuiButton mudarTamanho;
    private GuiButton add;
    private GuiButton remove;
    private GuiButton clear;
    
    int x;
    int y;
    
    
    public Lista(){
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
        x = 100;
        y = 400;
        indiceUltimo = 0;
        tamanho = 10; //valor padrao, pode ser mudado
        inicio = 0;
        fim = 0;
        tamanhoFontBase = 30;
        tamanhoFontMin = 8;
        
        elementosLista = new int[tamanho];
        desenharOuNao = new boolean[tamanho];
        
        contornoFila = new Rectangle(x, y, 600, 70);
        
        add = new GuiButton(x, y - 100, 100, 50, "Add", this);
        remove = new GuiButton(x, y - 40, 100, 30, "Remove", this);
        mudarTamanho = new GuiButton(x + 150, y - 40, 100, 30, "Mudar tamanho", this);
        clear = new GuiButton(x + 150 , y - 100 , 100, 50, "Clear", this);
        
        inputAdd = new GuiInputDialog("Inserir dados",
                "Insira um elemento no inicio",
                "Cancelar", true, this);
 
        inputTamanho = new GuiInputDialog("Inserir tamanho", 
                "Insira o tamanho da fila",
                "Cancelar", true, this);
        
    }
    
    @Override
    public void update( double delta ){
        add.update(delta);
        remove.update(delta);
        mudarTamanho.update(delta);
        clear.update(delta);
        inputTamanho.update(delta);
        inputAdd.update(delta);
        
        if(add.isMousePressed()){
            inputAdd.show(280, 100);
        }
        
        /* teste
        if(add.isMouseOver()){
            desenhar = true;
        }else{
            desenhar = false;
        }*/
    
        if ( inputAdd.isCloseButtonPressed() || inputAdd.isCancelButtonPressed() ) {
            inputAdd.hide();
        }
        
        
        //Add
        if(inputAdd.isOkButtonPressed() || inputAdd.isEnterKeyPressed()){
            if(ehInt(inputAdd.getValue())){
                
                if(indiceUltimo < tamanho){
                    
                    for(int i = indiceUltimo; i >= 0; i--){
                        elementosLista[i + 1] = elementosLista[i];
                    }
                    
                    elementosLista[0] = Integer.parseInt(inputAdd.getValue());                  
                    inicio = elementosLista[0];
                    fim = elementosLista[indiceUltimo];
                    indiceUltimo++;
                    inputAdd.hide();
                    
                }
            } else{
                inputAdd.setText("Precisa ser um numero");
            }
        }
        
        //Remove
        if(remove.isMousePressed()){
            for(int i = 0; i < tamanho - 1; i++){
                elementosLista[i] = elementosLista[i + 1]; 
            }
            
            if(indiceUltimo > 0){
                indiceUltimo--;
            }
            
            inicio = elementosLista[0];
            fim = elementosLista[indiceUltimo];
        }
        
        
        //clear
        if(clear.isMousePressed()){
            elementosLista = new int[tamanho];
            indiceUltimo = 0;
            
            //precisa repetir esse codigo p/ atualizar o inicio;
            inicio = elementosLista[0];
            fim = elementosLista[0];
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
                elementosLista = new int[Integer.parseInt(inputTamanho.getValue())];
                             
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
        desenharDeque(contornoFila, elementosLista);
        add.draw();
        remove.draw();
        clear.draw();
        mudarTamanho.draw();
        inputTamanho.draw();
        inputAdd.draw();
        /*if(desenhar){
            add.draw();
        }*/
     
    }
    
    public void desenharDeque(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = array.length;
        int tamanhoFont = Math.max(tamanhoFontBase - array.length,  tamanhoFontMin);
        
        for(int i = 0; i < tamanho; i++){
            double yPos = contorno.y;
            double xPos = contorno.x + (contorno.width / tamanhoElemento) * i;
            
            //desenhar o elemento
            drawText(Integer.toString(elementosLista[i]), 
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
        new Lista();
    }
}
