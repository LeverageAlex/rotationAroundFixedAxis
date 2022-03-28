import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Mathe3D extends Application {
	
	Timer t;
	boolean folgen = false;
	boolean rotieren = false;
	Cylinder cylinder;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(true);
        
        
        
        
        
        Box box = new Box(200, 400, 8);
        box.setMaterial(new PhongMaterial(Color.RED));
        
        Line line = new Line(100, 670,   100,   -700);
        line.setStrokeWidth(5.0);
        
        cylinder = new Cylinder(200, 400);
        
        cylinder.setLayoutX(100.0);
        
        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        Rotate ryBox = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rxBox = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        ryBox.setPivotX(100);
        ryBox.setPivotY(400);
      
        box.getTransforms().add(ryBox);
        box.getTransforms().add(rxBox);

        yRotate.setPivotY(700);
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(5);
        moveTo.setY(1);
       
        LineTo lineTo = new LineTo();
        lineTo.setX(1);
        lineTo.setY(1);
       
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        path.setStrokeWidth(0.2);
        path.setStroke(Color.BLACK);
       
        

        
        

        // Create and position camera
       

        // animate the camera position.
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15), 
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        

        
        // Build the Scene Graph
        Group root = new Group();       
         root.getChildren().add(box);;
        root.getChildren().add(path);
        root.getChildren().add(line);


        // set the pivot for the camera position animation base upon mouse clicks on objects


        // Use a SubScene


        Button button2 = new Button("rotieren");
        
        button2.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {

		    t = new Timer();
		    rotieren = rotieren == false;
		    timeraktuell(ryBox, rxBox);
		    }
		});
        button2.setLayoutX(500);
        button2.setLayoutY(100);
        root.getChildren().add(button2);
        
        
 Button button3 = new Button("folgen");
        
        button3.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {

		    startSleep(root, box, ryBox);
		    folgen = folgen == false;
		    timeraktuell(ryBox, rxBox);
		    }
		});
        button3.setLayoutX(500);
        button3.setLayoutY(200);
        root.getChildren().add(button3);
        
        
Button button4 = new Button("Säubern");
button4.setRotationAxis(Rotate.X_AXIS);
button4.setRotate(0);
        button4.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {

		    		
		        root.getChildren().removeAll();
		        try {
					start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


		    }
		});
        button4.setLayoutX(500);
        button4.setLayoutY(300);
        root.getChildren().add(button4);
        
        
        Button button5 = new Button("Zylinder");
        button5.setRotationAxis(Rotate.X_AXIS);
        button5.setRotate(0);
                button5.setOnAction(new EventHandler<ActionEvent>() {
        			 
        		    @Override
        		    public void handle(ActionEvent event) {

        		    		
        		       root.getChildren().add(cylinder);
        		    


        		    }
        		});
                button5.setLayoutX(500);
                button5.setLayoutY(400);
                root.getChildren().add(button5);

        t = new Timer();

        
        
        
        
        
        
        root.setLayoutX(300);
        
        
        
        
        root.setLayoutY(500);
        
        Scene scene = new Scene(root, 900, 900);
        scene.setFill(Color.rgb(10, 10, 40));
	    PerspectiveCamera camera = new PerspectiveCamera();
	    scene.setCamera(camera);
	    camera.setRotationAxis(Rotate.X_AXIS);
	   camera.setRotate(-25);
        scene.setFill(Color.ALICEBLUE);
        scene.setCamera(camera);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
        

        
        
    }
    
    
    
    public void startSleep(Group root, Box box, Rotate ryBox) {
    	
		 Task<Void> sleeper = new Task<Void>() {
	            @Override
	            protected Void call() throws Exception {
	                try {
	                	Thread.sleep(5);
	                	
	                } catch (InterruptedException e) {
	                }
	                return null;
	            }
	        };
	        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent event) {
	            	Box kek = new Box();
					
					
	        							kek.setWidth(box.getWidth());
	        							kek.setHeight(box.getHeight());
	        							kek.setDepth(box.getDepth());
	        		                    Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
	        		            		ry.setPivotX(100);
	        		            		ry.setPivotY(150);
	        		                    ry.setAngle(ryBox.getAngle());
	        		                    kek.getTransforms().add(ry);
	        		                    kek.setMaterial(new PhongMaterial(Color.RED));
	            	root.getChildren().add(kek);
	            	if(folgen) {
	            	startSleep(root, box, ryBox);
	            	}
	            }
	        });
	        new Thread(sleeper).start();
    	
    	
    	
    }
    
    
    
    public void timeraktuell(Rotate ryBox, Rotate rxBox) {
		 t.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					ryBox.setAngle(ryBox.getAngle() + 1);
					
					if(ryBox.getAngle() >= 360) {
						ryBox.setAngle(0);
					}
					if(rotieren) {
					timeraktuell(ryBox, rxBox);
					}

		    }
		}, 55);
		
	}
    
    
    
    

    public static void main(String[] args) {
        launch(args);
    }
}