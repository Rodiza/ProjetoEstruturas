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
    private boolean[] temElemento;
    //desenharOuNao serve como um array paralelo ao elementosLista
    //que vai dizer se um elemento foi ou nao inserido nessa posicao
    private boolean desenhar;
    
    private int tamanho;
    private int inicio;
    private int fim;
    private int indiceUltimo;
    private int indiceAdd;
    private int tamanhoFontBase;
    private int tamanhoFontMin;
    
    private Rectangle contornoFila;
    
    private GuiInputDialog inputTamanho;
    private GuiInputDialog inputAdd;
    private GuiInputDialog inputAddIndice;
    private GuiInputDialog inputRemove;
    
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
            "Lista",             // título                       / title
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
        temElemento = new boolean[tamanho];
        
        contornoFila = new Rectangle(x, y, 600, 70);
        
        add = new GuiButton(x, y - 100, 100, 50, "Add", this);
        remove = new GuiButton(x, y - 40, 100, 30, "Remove", this);
        mudarTamanho = new GuiButton(x + 150, y - 40, 100, 30, "Mudar tamanho", this);
        clear = new GuiButton(x + 150 , y - 100 , 100, 50, "Clear", this);
        
        inputAddIndice = new GuiInputDialog("Inserir índice",
                "Insira o índice",
                "Cancelar", true, this);
        
        inputAdd = new GuiInputDialog("Inserir dados",
                "Insira um elemento",
                "Cancelar", true, this);
 
        inputTamanho = new GuiInputDialog("Inserir tamanho", 
                "Insira o tamanho da fila",
                "Cancelar", true, this);
        
        inputRemove = new GuiInputDialog("Inserir indice",
                "insira o indice a ser removido",
                "Cancelar", true, this);
        
    }
    
    @Override
    public void update( double delta ){
        add.update(delta);
        remove.update(delta);
        mudarTamanho.update(delta);
        clear.update(delta);
        inputTamanho.update(delta);
        inputAddIndice.update(delta);
        inputAdd.update(delta);
        inputRemove.update(delta);
        
        if(add.isMousePressed()){
            inputAddIndice.show(280, 100);
        }
        
        /* teste
        if(add.isMouseOver()){
            desenhar = true;
        }else{
            desenhar = false;
        }*/
        
        
        //escolher o indice
        if(inputAddIndice.isOkButtonPressed() || inputAddIndice.isEnterKeyPressed()){
            String valorInserido = inputAddIndice.getValue();
            
            if(ehInt(valorInserido)){
                
                int indice = Integer.parseInt(valorInserido);
                boolean ehIndiceValido = indice < tamanho && indice >= 0;
                
                if(ehIndiceValido){
                   
                    indiceAdd = Integer.parseInt(inputAddIndice.getValue());
                    inputAddIndice.hide();
                    inputAdd.show(280, 100);
                    indiceAdd = indice;
                    
                } else{
                    inputAddIndice.setTitleBarBackgroundColor(RED);
                    inputAddIndice.setText("Esse índice não existe");
                }  
            } else{
                inputAddIndice.setTitleBarBackgroundColor(RED);
                inputAddIndice.setText("Precisa ser um numero");
            }
        }
        
        if ( inputAddIndice.isCloseButtonPressed() || inputAddIndice.isCancelButtonPressed() ) {
            inputAddIndice.hide();
        }
    
        if ( inputAdd.isCloseButtonPressed() || inputAdd.isCancelButtonPressed() ) {
            inputAdd.hide();
        }
        
        
        //Add
        if(inputAdd.isOkButtonPressed() || inputAdd.isEnterKeyPressed()){
            if(ehInt(inputAdd.getValue())){
                
               elementosLista[indiceAdd] = Integer.parseInt(inputAdd.getValue());
               temElemento[indiceAdd] = true;
               inputAdd.hide();
               
            } else{
                inputAdd.setTitleBarBackgroundColor(RED);
                inputAdd.setText("Precisa ser um número");
            }
        }
        
        if(remove.isMousePressed()){
            inputRemove.show();
        }
        
        //Remove
        if(inputRemove.isOkButtonPressed() || inputRemove.isEnterKeyPressed()){
            String valorInserido = inputRemove.getValue();
            
            if(ehInt(valorInserido)){
                int indice = Integer.parseInt(valorInserido);
                boolean ehIndiceValido = indice <= tamanho && indice >= 0;
                
                if(ehIndiceValido && temElemento[indice]){
                    temElemento[indice] = false;
                    elementosLista[indice] = 0;
                    inputRemove.hide();
                } else{
                    inputRemove.setTitleBarBackgroundColor(RED);
                    inputRemove.setText("Índice inválido");
                }
                
            } else{
                inputRemove.setTitleBarBackgroundColor(RED);
                inputRemove.setText("Precisa ser um número");
            }
        }
        
        if ( inputRemove.isCloseButtonPressed() || inputRemove.isCancelButtonPressed() ) {
            inputRemove.hide();
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
            
            
            if(ehInt(inputTamanho.getValue()) && Integer.parseInt(inputTamanho.getValue()) > 0){
                
                tamanho = Integer.parseInt(inputTamanho.getValue());
                temElemento = new boolean[tamanho];
                inputTamanho.hide();
                elementosLista = new int[Integer.parseInt(inputTamanho.getValue())];
                             
            }else{
                inputTamanho.setTitleBarBackgroundColor(RED);
                inputTamanho.setText("Número inválido");
            }
        }
        
        if(inputTamanho.isCloseButtonPressed() || inputTamanho.isCancelButtonPressed()){
            inputTamanho.hide();
        }
        
        
    }
    
    @Override
    public void draw(){
        desenharLista(contornoFila, elementosLista);
        add.draw();
        remove.draw();
        clear.draw();
        mudarTamanho.draw();
        inputTamanho.draw();
        inputAdd.draw();
        inputRemove.draw();
        inputAddIndice.draw();
        /*if(desenhar){
            add.draw();
        }*/
     
    }
    
    public void desenharLista(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = array.length;
        int tamanhoFont = Math.max(tamanhoFontBase - array.length,  tamanhoFontMin);
        
        for(int i = 0; i < tamanho; i++){
            double yPos = contorno.y;
            double xPos = contorno.x + (contorno.width / tamanhoElemento) * i;
            
            //desenhar o elemento
            if(temElemento[i]){
                drawText(Integer.toString(elementosLista[i]), 
                        new Vector2(xPos + 10, yPos + 10),
                        tamanhoFont,
                        BLACK);
            }
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
