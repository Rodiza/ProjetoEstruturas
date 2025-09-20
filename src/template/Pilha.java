package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;
import java.util.List;

public class Pilha extends EngineFrame {
    
    private int[] elementosPilha;
    private int topo;
    
    private GuiInputDialog inputDialog;
    private GuiButton inputButton;
    private GuiButton inputConfirm;
    
    
    int x;
    int y;
    int contador;
    
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
        x = 350;
        y = 200;
        contador = 0;
        elementosPilha = new int[10];
        
        inputButton = new GuiButton(x, y, 100, 100, "teste", this);
        inputDialog = new GuiInputDialog("Inserir dados", 
                "Insira um elemento na pilha", "Cancelar", true, this);
        
      
    }

    @Override
    public void update( double delta ) {
        inputButton.update(delta);
        inputDialog.update(delta);
        
        //abrir a aba de input
        if(inputButton.isMousePressed()){
            inputDialog.show();
        }
        
        //fechar a aba de input
        if ( inputDialog.isCloseButtonPressed() ) {
            inputDialog.hide();
        }
        
        //push
        if(inputDialog.isOkButtonPressed() || inputDialog.isEnterKeyPressed()){
            elementosPilha[contador] = Integer.parseInt(inputDialog.getValue());
            contador++;
            topo = Integer.parseInt(inputDialog.getValue());
            inputDialog.hide();
        }
    }
    
    @Override
    public void draw() {  
        inputButton.draw();
        inputDialog.draw();
    }
    
    public static void main( String[] args ) {
        new Pilha();
    }
    
}
