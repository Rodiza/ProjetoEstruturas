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
    private int topo;
    
    private GuiInputDialog inputPush;
    private GuiButton push;
    private GuiButton pop;
    
    private Rectangle contornoPilha;
    private Vector2 ponto;
    
    
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
        elementosPilha = new int[10];
        
        ponto = new Vector2(x, y);
        push = new GuiButton(x, y, 100, 70, "Push", this);
        pop = new GuiButton(x, y + 100, 100, 70, "Pop", this);
        inputPush = new GuiInputDialog("Inserir dados", 
                "Insira um elemento na pilha", "Cancelar", true, this);
        
        contornoPilha = new Rectangle(x - 350, y - 50 , 100, 300);
        
      
    }

    @Override
    public void update( double delta ) {
        push.update(delta);
        pop.update(delta);
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

                topo = Integer.parseInt(inputPush.getValue());
                inputPush.hide();

                ponto.y += 20;
                contador++;
            }else{
                inputPush.setText("Precisa ser um numero");
            }
        }
        
        //pop
        if(pop.isMousePressed()){
            //TODO
        }
    }
    
    @Override
    public void draw() {  
        push.draw();
        pop.draw();
        desenharPilha(contornoPilha, elementosPilha);
        inputPush.draw();
        
        
            
        
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
    
    //Desenha uma pilha de tamanho fixo 10
    public void desenharPilha(Rectangle contorno, int[] array){
        contorno.draw(this, BLACK);
        double tamanhoElemento = contorno.height / elementosPilha.length;
        
        for(int i = 0; i < contador; i++){
            //Começar desenhando da base da pilha
            double yPos = contorno.y + contorno.height - (i * tamanhoElemento);
            double xPos = contorno.x + (contornoPilha.width / 2);
            
            drawText(Integer.toString(elementosPilha[i]), 
                    new Vector2(xPos, yPos),
                    20,
                    BLACK
                    );
        }
        
        
        
        //Rectangle quadrado = new Rectangle(retangulo.x, retangulo.y, 
        //        100, tamanhoElemento);
        //quadrado.draw(this, BLACK);
        
    }
    
    
    public static void main( String[] args ) {
        new Pilha();
    }
    
}
