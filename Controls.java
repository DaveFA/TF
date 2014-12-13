// necessary imports to create control panel as separate window
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;
import java.awt.Frame;
import controlP5.*;


// control panel class
public class Controls extends PApplet{
  
  
  
  //
  //
  // CS6630 - START - you can start editing here
  //
  //
  
  
  boolean redBox = false; 
  boolean greenBox = false; 
  boolean blueBox = false;
  boolean alphaBox = false;
  
  int redDots[] = new int[256]; 
  int greenDots[] = new int[256]; 
  int blueDots[] = new int[256];
  int alphaDots[] = new int[256];
  
  // set the height & width of control panel
  int cWidth = 800;
  int cHeight = 500;
  
  
  
  // data variables:
  
    // size of our uniform 3D grid
    int gridSize;
    
    // data[x][y][z]
    // stores 256 possible values
    int[][][] data;
    
    // min & max values for data (0 & 255, respectively)
    int dataMin, dataMax;
  
  
  
  // custom transfer function arrays - one for each RGBA channel
    
    // length 256; our array values are also mapped from 0 to 255
    // you must call updateTransferFunction() to update volume renderer!
    int[] red;
    int[] green;
    int[] blue;
    int[] alpha;
  
  
  
  // Processing setup method (runs once)
  public void setup(){
    
    // load the volume rendering data
    loadData();
    
    // create the control panel interface
    createControlPanel();
    
    // initalize our custom transfer function (all values as transparent white)
    initializeTransferFunction();
    
    // my setup code
    mySetup();
    
  }
  
  
  
  // Processing draw method (runs repeatedly)
  public void draw(){
    background(255);
    
    // my widget code
    println(mouseX, mouseY);

    if(mousePressed == true)
    {
       // select a box; 
       selectBox();
    }
    if(mousePressed == true)
    {
      if(mouseX > 400 && mouseX < 655 && mouseY > 100 && mouseY < 355)
      {
        if(alphaBox)
        {
          alphaDots[mouseX - 400] = mouseY-100;
        }

        if(redBox)
        {
          redDots[mouseX - 400] = mouseY-100;
        }

       if(greenBox)
        {
          greenDots[mouseX - 400] = mouseY-100;
        }
        
        if(blueBox)
        {
          blueDots[mouseX - 400] = mouseY-100;
        }
        
                
      }
      
    } 
    drawBoxes();
    drawDots();
    
    
    
    // update our custom transfer function
    // you may wish to only call this when you change the data
    updateTransferFunction();
  

}
  
  
  public void mySetup()
  {
    
    for(int i = 0; i < 256; i++)
    {
//      redDots[i] = (int)random(0,255);
//      greenDots[i] = (int)random(0,255);
//      blueDots[i] = (int)random(0,255);
//      alphaDots[i] = 150;

      redDots[i] = 0;
      greenDots[i] = 0;
      blueDots[i] = 0;
      alphaDots[i] = 0;
   
    }
  
  }
  
  
  public void drawDots()
  {
    
    // for alpha
    strokeWeight(2);
    beginShape(LINES);
    for(int i=0; i<256; i++)
    {
      vertex(400+i, alphaDots[i]+100);
    }
    endShape();
    strokeWeight(1);
    
    // for red 
    stroke(255, 0, 0);
    strokeWeight(2);
    beginShape(LINES);
    for(int i=0; i<256; i++)
    {
      vertex(400+i, redDots[i]+100);
    }
    endShape();
    strokeWeight(1);
    stroke(0);
        
    // for green 
    stroke(0, 255, 0);
    strokeWeight(2);
    beginShape(LINES);
    for(int i=0; i<256; i++)
    {
      vertex(400+i, greenDots[i]+100);
    }
    endShape();
    strokeWeight(1);
    stroke(0);
    
    
    // for blue 
    stroke(0, 0, 255);
    strokeWeight(2);
    beginShape(LINES);
    for(int i=0; i<256; i++)
    {
      vertex(400+i, blueDots[i]+100);
    }
    endShape();
    strokeWeight(1);
    stroke(0);
    
    
  }
  
  
  public void drawBoxes()
  {
    // draw red 
    fill(255, 0, 0);
    if(redBox)
    {
      strokeWeight(4);
    }
    rect(400, 30, 50, 50);
    strokeWeight(1);
    
    
    // draw green
    fill(0, 255, 0);
    if(greenBox)
    {
      strokeWeight(4);
    }
    rect(500, 30, 50, 50);
     strokeWeight(1);
     
   
    // draw blue
   fill(0, 0, 255);
   if(blueBox)
   {
     strokeWeight(4);
   }
   rect(600, 30, 50, 50);
    strokeWeight(1);
    
  
   // draw alpha
   fill(170, 170, 170);
   if(alphaBox)
   {
     strokeWeight(4);
   }
   rect(700, 30, 50, 50);
   strokeWeight(1);
   
    
  }
  
  
  
  public void selectBox()
  {
      if(mouseX > 400 && mouseX < 450 && mouseY > 30 && mouseY <80)
      {
       redBox = true; 
       greenBox = false; 
       blueBox = false; 
       alphaBox = false; 
      }
  
      if(mouseX > 500 && mouseX < 550 && mouseY > 30 && mouseY <80)
      {
       greenBox = true; 
       redBox = false; 
       blueBox = false; 
       alphaBox = false; 

      }

      if(mouseX > 600 && mouseX < 650 && mouseY > 30 && mouseY <80)
      {
        blueBox = true; 
        redBox = false; 
        greenBox = false; 
        alphaBox = false; 
      }

      if(mouseX > 700 && mouseX < 750 && mouseY > 30 && mouseY <80)
      {
        alphaBox = true;        
        redBox = false; 
        greenBox = false; 
        blueBox = false; 

      }

}
  
  
  
  // load in our data to variables
  public void loadData(){
    int s = volren.data.length;
    gridSize = (int) pow((float) s, (float) (1.0 / 3.0));
    data = new int[gridSize][gridSize][gridSize];
    int dx = 0;
    int dy = 0;
    int dz = 0;
    dataMin = 255;
    dataMax = 0;
    for(int i = 0; i < s; i++){
      
      // store data
      int d = volren.data[i] & 0xFF;
      data[dx][dy][dz] = d;
      
      // check min, max values
      if(d < dataMin)
        dataMin = d;
      if(d > dataMax)
        dataMax = d;
      
      // prep next data value
      dx++;
      if(dx == gridSize){
        dy++;
        dx = 0;
        if(dy == gridSize){
          dz++;
          dy = 0;
        }
      }
    }
  }
  
  
  
  //
  //
  // CS6630 - END - no need to edit below
  //
  //
  
  
  
  // variables for the control panel
  ColorPicker tfCPick1;
  ColorPicker tfCPick2;
  RadioButton tfMode;
  ControlP5   cp5;
  PApplet     parent;
  VolumeRenderer volren;
  
  // set initial transfer function data (all values to transparent white)
  public void initializeTransferFunction(){
    red = new int[256];
    green = new int[256];
    blue = new int[256];
    alpha = new int[256];
    for(int i = 0; i < 256; i++){
      red[i] = 255;
      green[i] = 255;
      blue[i] = 255;
      alpha[i] = 0;
    }
    
    // pass in our initial custom transfer function
    updateTransferFunction();
  }
  
  // update custom transfer function data & pass to volume renderer
  public void updateTransferFunction(){
    
    // my code 
    
   for(int i = 0; i < 256; i++){
      red[i] = (int)random(0,255);
      green[i] = (int)random(0,255);
      blue[i] = (int)random(0,255);
      alpha[i] = 150;
   
    }
    
    volren.customRed = red;
    volren.customGreen = green;
    volren.customBlue = blue;
    volren.customAlpha = alpha;
    parent.redraw();
  }
  
  // create control panel interface
  public void createControlPanel(){
    
    // setup P5 library
    cp5 = new ControlP5(this);
    frameRate(25);
    
    // variables for creating control panel
    int y = -10, height = 15, spacing = 20;
    
    // dataset selector
    float status3[] = { 0, 0, 0, 1, 0, 0 };
    cp5.addTextlabel("label5", "Data Set")
      .setPosition( 10, y+=spacing )
      .setHeight(48)
      .setColor( parent.color(17, 17, 17) );
    cp5.addRadio( "dataset", 10, y+=spacing )
      .setSize( 20, height )
      .setColorForeground(color(180, 180, 180))
      .setColorBackground(color(180, 180, 180))
      .setItemsPerRow(3)
      .setSpacingColumn( 50 )
      .addItem( "aneurism", 0 )
      .addItem( "bonsai", 1 )
      .addItem( "bucky", 2 )
      .addItem( "foot", 3 )
      .addItem( "fuel", 4 )
      .addItem( "skull", 5 )
      .setColorLabel( parent.color(75, 75, 75) )
      .setArrayValue( status3 )
      .plugTo( this, "setData" );
    
    // lighting settings
    y += spacing + height;
    cp5.addTextlabel( "label1", "Light Settings" )
      .setPosition( 10, y+=spacing )
      .setColor( parent.color(17, 17, 17) );
    cp5.addToggle("enabled")
      .setPosition( 190, y )
      .setSize( 20, height-5 )
      .setColorForeground(color(180, 180, 180))
      .setColorBackground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setValue( volren.lightEnabled )
      .plugTo( volren, "lightEnabled" )
      .getCaptionLabel()
        .setPaddingX( 5 )
        .align( ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER );
    cp5.addSlider("ambient")
      .setPosition( 10, y+=spacing )
      .setSize( 200, height )
      .setColorBackground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setRange( 0, 1 ) 
      .setValue( volren.lightAmbient )
      .plugTo( volren, "lightAmbient" );
    cp5.addSlider("diffuse")
      .setPosition( 10, y+=spacing )
      .setSize( 200, height )
      .setColorBackground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setRange( 0, 1 )
      .setValue( volren.lightDiffuse )
      .plugTo( volren, "lightDiffuse" );
    cp5.addSlider("specular")
      .setPosition( 10, y+=spacing )
      .setSize( 200, height )
      .setColorBackground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setRange( 0, 1 )
      .setValue( volren.lightSpecular )
      .plugTo( volren, "lightSpecular" );
    cp5.addSlider("exponent")
      .setPosition( 10, y+=spacing )
      .setSize( 200, height )
      .setColorBackground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setRange( 1, 50 )
      .setValue( volren.lightExponent )
      .plugTo( volren, "lightExponent" );
    
    // sampling settings (removed)
    // y += 10;
    // cp5.addTextlabel( "label3", "Sampling Settings" )
    //   .setPosition( 10, y+=spacing )
    //   .setColor( parent.color(255,200,0) );
    // cp5.addSlider( "Step", 0.001f, 0.01f, 10, y+=spacing, 200, height )
    //   .setDecimalPrecision( 5 )
    //   .setValue( 0.005f )
    //   .plugTo( volren, "sampleStep" );
    
    // composite settings (removed)
    // y += 10;
    // float[] status1 = { 1, 0 };
    // cp5.addTextlabel( "label4", "Compositing" )
    //   .setPosition( 10, y+=spacing )
    //   .setColor( parent.color(255,200,0) );
    // cp5.addRadio( "compositeMode", 10, y+=spacing )
    //   .setSize( 30, height )
    //   .setItemsPerRow(3)
    //   .setSpacingColumn( 40 )
    //   .addItem( "LEVOY", 0 )
    //   .addItem( "MIP", 1 )
    //   .setArrayValue( status1 )
    //   .plugTo( this, "setCompositeMode" );
    
    // transfer function settings
    y += 10;
    cp5.addTextlabel( "label2", "Transfer Function Settings" )
      .setPosition( 10, y+=spacing )
      .setColor( parent.color(17, 17, 17) );
    cp5.addSlider( "Center", 0, 255, 10, y+=spacing, 200, height )
      .setColorBackground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setValue( 77 )
      .plugTo( volren, "tfCenter" );
    // cp5.addSlider( "Width", 0, 1, 10, y+=spacing, 200, height )
    //   .setValue( 0.1f )
    //   .plugTo( volren, "tfWidth" );
    cp5.addSlider( "Density", 0, 40, 10, y+=spacing, 200, height )
      .setColorBackground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setValue( 5.0f )
      .plugTo( volren, "tfDensity" );
    
    // transfer function mode
    y += 10;
    float[] status2 = {1, 0};
    cp5.addRadio( "tfMode", 10, y+=spacing )
      .setSize( 20, height )
      .setItemsPerRow(4)
      .setSpacingColumn( 40 )
      .addItem( "STEP", 0 )
      // .addItem( "RECT", 1 )
      // .addItem( "HAT", 2 )
      // .addItem( "BUMP", 3 )
      .addItem( "CUSTOM TRANSFER FUNCTION", 4 )
      // .addItem( "CUSTOM2", 5 )
      // .addItem( "CUSTOM3", 6 )
      // .addItem( "CUSTOM4", 7 )
      .setColorBackground(color(180, 180, 180))
      .setColorForeground(color(180, 180, 180))
      .setColorLabel( parent.color(75, 75, 75) )
      .setArrayValue( status2 )
      .plugTo( this, "setTFMode" );
    
    // transfer function color picker
    y += 15 + spacing;
    cp5.addColorPicker( "tfColor1", 10, y+=height, 200, 10 )
      .setColorLabel( parent.color(75, 75, 75) )
      .setColorValue( volren.tfColor1 )
      .plugTo( this, "setTFColor1" );
    // cp5.addColorPicker( "tfColor2", 10, y+=70, 200, 10 )
    //   .setColorValue( volren.tfColor2 )
    //   .plugTo( this, "setTFColor2" );
    
    // update control panel
    cp5.addCallback(new RedrawListener(parent));
  }
  
  // set transfer function mode
  public void setTFMode(int c){
    volren.tfMode = c;
    parent.redraw();
  }
  
  // set transfer function color
  public void setTFColor1(int c){
    volren.tfColor1 = c;
    parent.redraw();
  }
  
  // select our dataset
  public void setData(int c){
    
    // set data to load
    if(c == 0)
      volren.dataName = "aneurism";
    else if(c == 1)
      volren.dataName = "bonsai";
    else if(c == 2)
      volren.dataName = "bucky";
    else if(c == 3)
      volren.dataName = "foot";
    else if(c == 4)
      volren.dataName = "fuel";
    else if(c == 5)
      volren.dataName = "skull";
    
    // load data
    volren.data = parent.loadBytes(volren.dataName + ".raw");
    loadData();
    
    // update volume renderer
    parent.redraw();
  }
  
  // grab our controlp5 object
  public ControlP5 control(){
    return cp5;
  }
  
  // creates our window
  Controls(PApplet parent, VolumeRenderer volren){
    this.parent = parent;
    this.volren = volren;
    
    Frame f = new Frame("Controls");
    f.add(this);
    
    init();
    
    f.setTitle("Control Panel");
    f.setSize(cWidth, cHeight);
    f.setLocation(100, 100);
    f.setResizable(false);
    f.setVisible(true);
  }
  
  // only update screen when necessary
  class RedrawListener implements CallbackListener{
    PApplet target;
    
    RedrawListener(PApplet target){
      this.target = target;
    }
    
    public void controlEvent(CallbackEvent event){
      if(event.getAction() == ControlP5.ACTION_BROADCAST)
        target.redraw();
    }
  }
  
  // set composite mode (removed)
  // public void setCompositeMode(int c){
  //   volren.compositeMode = c;
  //   parent.redraw();
  // }
  
  // set second transfer function color (removed)
  // public void setTFColor2(int c){
  //   volren.tfColor2 = c;
  //   parent.redraw();
  // }
}
