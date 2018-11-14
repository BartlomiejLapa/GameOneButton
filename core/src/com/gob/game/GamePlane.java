package com.gob.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;



public class GamePlane extends ApplicationAdapter{
    
    private Music music;
    private Texture playerTexture, treeTexture;
    private Player player;
    private Tree tree;
    private Array<Tree> treeArray;
    private OrthographicCamera camera;
    
    public static int SCREEN_WIDTH = 1024;    
    public static int SCREEN_HEIGHT = 520;
    private static float SPEED_MOVE= 5f;
    private float gravity = -50;
    private int healt = 10;
    
    SpriteBatch batch;

   
    @Override
    public void create() {
        loadData();
        init();

    }

    private void loadData() {
        playerTexture = new Texture("airplane.png");
        treeTexture = new Texture("tree.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
        music.setLooping(true);
        
    }

    private void init() {
         batch = new SpriteBatch();
         music.play();
         camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
         
         player = new Player(playerTexture);
         treeArray = new Array<Tree>();
         
         for (int i=1; i<50; i++){
             Tree tree = new Tree(treeTexture);
             tree.x = (MathUtils.random(SCREEN_WIDTH/5, SCREEN_WIDTH-SCREEN_WIDTH/10))*i;             
             tree.y = 0;
             treeArray.add(tree);
         }
         player.x = -450;
    }
 
    @Override
    public void render() {
        
        update();
        Gdx.gl.glClearColor((float) (20/255.0), (float)(155/255.0),(float)(217/255.0), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for(Tree tree:treeArray){
            tree.draw(batch);
        }
        player.draw(batch);     
        batch.end();   
    }
    
    float i = 0;
    
    private void update() {
        handleInput();
        
        camera.position.set(i+= SPEED_MOVE, SCREEN_HEIGHT/2, 0);
        camera.update();
        player.y += player.fly * Gdx.graphics.getDeltaTime();
        player.x += SPEED_MOVE ;
    
  
        
        if(player.y > 0 && player.y <= SCREEN_HEIGHT+10) player.fly += gravity;
        else if (player.y > SCREEN_HEIGHT+10) player.y = SCREEN_HEIGHT-50;
        else player.y = 0;
      
    

        
//        if(player.overlaps(tree)) 

    }

    private void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
//           player.y += 380 * Gdx.graphics.getDeltaTime();
            player.fly();
               
        }
   

    }
    
    @Override
    public void dispose() { 
        batch.dispose();
    }


}
