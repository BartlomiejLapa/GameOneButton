package com.gob.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;



public class GamePlane extends ApplicationAdapter{
    
    private Sound soundFly;
    private Music music;
    private Texture playerTexture, treeTexture, playerHealth, cloudTexture;
    private Player player;
    private Tree tree;
    private Array<Tree> treeArray;
    private Array<Cloud> cloudArray;
    private ArrayList<Explosion> explosion;
    private OrthographicCamera camera;
    
    public static int SCREEN_WIDTH = 1024;    
    public static int SCREEN_HEIGHT = 520;
    private static float SPEED_MOVE= 5f;
    private float gravity = -80;
    protected static float HEALTH = 1000;
    
    SpriteBatch batch;
    

    
   
    @Override
    public void create() {
        loadData();
        init();
}

    private void loadData() {
        playerTexture = new Texture("airplane.png");
        treeTexture = new Texture("tree.png");
        cloudTexture = new Texture("cloud.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
        music.setLooping(true);
        playerHealth = new Texture ("blank.png");
        soundFly = Gdx.audio.newSound(Gdx.files.internal("soundPlayer.ogg"));
        
    }

    private void init() {
         batch = new SpriteBatch();
         music.play();
         camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
         
         player = new Player(playerTexture, playerHealth);
         treeArray = new Array<Tree>();
         cloudArray = new Array<Cloud>();
         explosion = new ArrayList<Explosion>();
         
         for (int i=1; i<50; i++){
             Tree tree = new Tree(treeTexture);
             tree.x = (MathUtils.random(SCREEN_WIDTH/5, SCREEN_WIDTH-SCREEN_WIDTH/8))*i;             
             tree.y = 0;
             treeArray.add(tree);
         }
         
         for (int i=1; i<100; i++){
             Cloud cloud = new Cloud(cloudTexture);
             cloud.x = (MathUtils.random(200,600))*i;             
             cloud.y = MathUtils.random(SCREEN_HEIGHT-200, SCREEN_HEIGHT-10);
             cloudArray.add(cloud);
         }
         
         player.x = -450;
         
         
         
         ArrayList<Explosion> explosionsRemove = new ArrayList<Explosion>();
         for (Explosion explosion : explosion){
             explosion.update(1);
             if (explosion.remove)
                 explosionsRemove.add(explosion);
         }
         explosion.remove(explosionsRemove);
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
            if (player.overlaps(tree)) {
                soundFly.play();
                HEALTH-=7;
                explosion.add(new Explosion(player.getX()+(player.width/4)*3, player.getY()));
                
            }
        }
        
        for(Cloud cloud:cloudArray){
            cloud.draw(batch);
        }
        
        for (Explosion explosion : explosion) 
            explosion.render(batch);
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
        
        if (HEALTH <= 0) Gdx.app.exit();
        if (HEALTH <1000) HEALTH+=1;
    
  
        
        if(player.y > 0 && player.y <= SCREEN_HEIGHT+10) player.fly += gravity;
        else if (player.y > SCREEN_HEIGHT+10) player.y = SCREEN_HEIGHT-50;
        else player.y = 0;
         
        

    }

    private void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
            player.fly();
        }
   

    }
    
    @Override
    public void dispose() { 
        batch.dispose();
    }


}
