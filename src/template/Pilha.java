package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.List;

public class Pilha extends EngineFrame {
    
    private int[] elementosPilha;
    int tamanhoFila;
    private int topo;
    
    private GuiInputDialog inputPush;
    private GuiInputDialog inputTamanho;
    private GuiButton push;
    private GuiButton pop;
    
    private Rectangle contornoPilha;
    
    
    private int x;
    private int y;
    private int contador;
    
    public Pilha() {
        
        super(
            800,                 // largura                      / width
            600,                 // algura                       / height
            "Pilha",             // título                       / title
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
        x = 500;
        y = 200;
        contador = 0;
        elementosPilha = new int[30];
       
        push = new GuiButton(x, y, 100, 70, "Push", this);
        pop = new GuiButton(x, y + 100, 100, 70, "Pop", this);
        
        inputPush = new GuiInputDialog("Inserir dados", 
                "Insira um elemento na pilha", "Cancelar", true, this);
        inputTamanho = new GuiInputDialog("Inserir tamanho", 
                "Insira o tamanho da fila",
                "Cancelar", false, this);
        
        contornoPilha = new Rectangle(x - 350, y - 50 , 100, 300);
        
      
    }

    @Override
    public void update( double delta ) {
        push.update(delta);
        pop.update(delta);
        inputTamanho.update(delta);
        inputPush.update(delta);
       
        
        //abrir a aba de input
        if(push.isMousePressed()){
            inputPush.show();
        }
       
        //fechar a aba de input
        if ( inputPush.isCloseButtonPressed() ) {
            inputPush.hide();
        }
        
        //push
        if(inputPush.isOkButtonPressed() || inputPush.isEnterKeyPressed()){
            if(ehInt(inputPush.getValue())){
                elementosPilha[contador] = Integer.parseInt(inputPush.getValue());

                topo = elementosPilha[contador];
                inputPush.hide();
                contador++;
            }else{
                inputPush.setText("Precisa ser um numero");
            }
        }
        
        //pop
        if(pop.isMousePressed()){
            if(contador > 0){
                contador--; 
                topo = elementosPilha[contador - 1];
            }else{
                topo = 0;
            }
        }
    }
    
    @Override
    public void draw() {  
        push.draw();
        pop.draw();
        desenharPilha(contornoPilha, elementosPilha);
        inputPush.draw();
        inputTamanho.draw();
        
        
        //so desenha o topo se tiver elemento
        if(contador > 0){
            drawText("Topo: " + Integer.toString(topo), 
                    new Vector2(150, 100),
                    20,
                    BLACK);
        }
        
    }
    
    //Usado para chechar se a entrada é um int
    private boolean ehInt(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    //Desenha a pilha e o contorno dos numeros
    public void desenharPilha(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = contorno.height / array.length;
        
        for(int i = 0; i < contador; i++){
            //Começar desenhando da base da pilha
            double yPos = contorno.y + contorno.height - (i * tamanhoElemento) - tamanhoElemento;
            double xPos = contorno.x + (contorno.width / 2);
            
            //desenha o numero
            drawText(Integer.toString(elementosPilha[i]), 
                    new Vector2(xPos - 10, yPos + 5),
                    30 - array.length,
                    BLACK);
            
            //desenha o contorno do elemento
            drawRectangle(new Rectangle(contorno.x, 
                    yPos,
                    100, tamanhoElemento), BLACK);
        }
        
        
        
        
    }
    
    
    public static void main( String[] args ) {
        new Pilha();
    }
    
}
