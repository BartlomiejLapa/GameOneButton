package com.gob.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;

public class GamePlane extends ApplicationAdapter {
    
    private Sound soundFly;
    private Music music;
    private Texture playerTexture, treeTexture, playerHealth, cloudTexture, missileTexture;
    private Player player;
    private Missile missile, missile2;
    private Array<Tree> treeArray;
    private Array<Cloud> cloudArray;
    private ArrayList<Explosion> explosion;
    private OrthographicCamera camera;
    private boolean over = false;
    private BitmapFont font;
    private float i = 0;
 
    //SETTINGS:
    public final static int SCREEN_WIDTH = 1024;    
    public final static int SCREEN_HEIGHT = 520;
    private static float SPEED_MOVE= 6f;
    private float gravity = -80;
    protected static float HEALTH = 1000;
    private String pathPlayerTexture = "airplane.png";
    private String pathTreeTexture = "tree.png";
    private String pathCloudTexture = "cloud.png";
    private String pathMissileTexture = "missile.png";
    private String pathPlayerHealth = "blank.png";
    private String pathSoundFly = "soundPlayer.ogg";
    private String pathMusic = "music.ogg";
    protected static String pathExplosion = "explosion.png";
    
    SpriteBatch batch;
       
    @Override
    public void create() {
        loadData();
        init();
}

    private void loadData() {
        playerTexture = new Texture(pathPlayerTexture);
        treeTexture = new Texture(pathTreeTexture);
        cloudTexture = new Texture(pathCloudTexture);
        missileTexture = new Texture(pathMissileTexture);
        music = Gdx.audio.newMusic(Gdx.files.internal(pathMusic));
        music.setLooping(true);
        playerHealth = new Texture (pathPlayerHealth);
        soundFly = Gdx.audio.newSound(Gdx.files.internal(pathSoundFly));
    }

    private void init() {
         batch = new SpriteBatch();
         music.play();
         camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
         font = new BitmapFont();
         font.setColor(Color.RED);      
         
         player = new Player(playerTexture, playerHealth);
         treeArray = new Array<Tree>();
         cloudArray = new Array<Cloud>();
         explosion = new ArrayList<Explosion>();
         missile = new Missile(missileTexture);
         missile2 = new Missile(missileTexture);
         
         for (int i=1; i<80; i++){
             Tree tree = new Tree(treeTexture);
             tree.x = (MathUtils.random(SCREEN_WIDTH/5, SCREEN_WIDTH-SCREEN_WIDTH/4))*i;             
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
         missile.x = 800;
         missile2.x = 1600;

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
        
        if (player.overlaps(missile) || player.overlaps(missile2)) {
                soundFly.play();
                HEALTH-=40;
                explosion.add(new Explosion(player.getX()+(player.width/4)*3, player.getY()));       
            }
        
        for(Cloud cloud:cloudArray){
            cloud.draw(batch);
        }
        
        
                
        for (Explosion explosion : explosion) 
            explosion.render(batch);
        
        if (over == true) font.draw(batch, "GAME OVER", 0, 300);
        

        missile.draw(batch);
        missile2.draw(batch);
        player.draw(batch);     
        batch.end();   
      
    }
        
    private void gameOver() {
        camera.position.set(i= 0, SCREEN_HEIGHT/2, 0);
        camera.update();
        player.x = -450;
        over = true;
    }
    
    private void update() {
        handleInput();
        if(!over) {
            camera.position.set(i+= SPEED_MOVE, SCREEN_HEIGHT/2, 0);
            camera.update();
            player.y += player.fly * Gdx.graphics.getDeltaTime();
            player.x += SPEED_MOVE ;
            missile.x -= SPEED_MOVE*2;
            missile.y = 250;
            missile2.x -= SPEED_MOVE*3;
            missile2.y = 450;
                    
            
            
        } else gameOver();
        
        if (HEALTH <= 0) gameOver();
        if (HEALTH > 0 && HEALTH < 1000) HEALTH+=1;
    
        if(player.y > 0 && player.y <= SCREEN_HEIGHT+10) player.fly += gravity;
        else if (player.y > SCREEN_HEIGHT+10) player.y = SCREEN_HEIGHT-50;
        else player.y = 0;   
        
        if(missile.x < -300) missile.x=player.getX()+ MathUtils.random(1000, 8000);
        if(missile2.x < -400) missile2.x=player.getX()+ MathUtils.random(4000, 12000);
        
        
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
        font.dispose();
    }
}
