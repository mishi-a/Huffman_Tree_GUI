import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window; 
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class DisplaySimpleTree extends Application {

	Canvas canvas = null;
	public static int gap = 100;
	public static int vGap = 50;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Huffman Encoder Decoder");
        GridPane gridPane = createDisplayTreePane();
        Scene scene = new Scene(gridPane, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private GridPane createDisplayTreePane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();
        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(20, 10, 20, 20));
        // Set the horizontal gap between columns
        gridPane.setHgap(8);
        // Set the vertical gap between rows
        gridPane.setVgap(8);
        
        addUIControls(gridPane);
        //gridPane.setGridLinesVisible(true);
        
        FlowPane fp = new FlowPane();
        canvas = new Canvas(1500,1500);
        
   
        fp.getChildren().add(canvas);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(fp);
        scrollPane.setPannable(true);
        
        gridPane.add(scrollPane, 0,4,4,1);
        return gridPane;
    }

    private void addUIControls(final GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Huffman Encoder Decoder");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gridPane.add(headerLabel, 0,0,3,1);
        
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        //GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Name Label
        Label nameLabel = new Label("Enter a Text : ");
        GridPane.setHalignment(nameLabel, HPos.LEFT);
        nameLabel.setPrefWidth(100);
        nameLabel.setPrefHeight(40);
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        final TextField nameField = new TextField("Welcome");
        nameField.setPrefHeight(40);
        nameField.setPrefWidth(1000);
        gridPane.add(nameField, 2,1);


        // Add Email Label
        Label emailLabel = new Label("Enter a bit string :");
        GridPane.setHalignment(emailLabel, HPos.LEFT);
        gridPane.add(emailLabel, 0, 2);

        final TextField encodedString = new TextField();
        encodedString.setPrefHeight(40);
        encodedString.setPrefWidth(1000);
        gridPane.add(encodedString, 2, 2);
        
        final TextField outPut = new TextField();
        outPut.setPromptText("Output Will be Displayed Here");
        outPut.setPrefHeight(40);
        outPut.setPrefWidth(1000);
        gridPane.add(outPut, 2, 3);
        
        Button submitButton = new Button("Show Huffman Tree");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(150);
        gridPane.add(submitButton, 3, 1);
        
        Button submitButton1 = new Button("Decode To Text");
        submitButton1.setPrefHeight(40);
        submitButton1.setPrefWidth(150);
        gridPane.add(submitButton1, 3, 2);
   
        submitButton.setOnAction(new EventHandler<ActionEvent>() 
        {

			public void handle(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				if(nameField.getText().isEmpty())
				{
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter String");
				    return;
				}
				else 
				{
					String input = nameField.getText();
					HashMap<Character, String> map = Huffman.findHuffman(input.toLowerCase());
					System.out.println(map);
					int ht = Huffman.findHeight(Huffman.root);
					String disp = "";
					input = input.toLowerCase();
					for(int i=0;i<input.length();i++)
					{
						disp = disp + map.get(input.charAt(i));
						//System.out.println(input.charAt(i)+" " + disp);
					}
					outPut.setText(input+" is encoded as "+disp);
					GraphicsContext gc = canvas.getGraphicsContext2D();
					gc.clearRect(0,0,1500,1500);
					gc.setStroke(Color.BLUE);
					gc.setFont(new Font("Arial",20));
					double begin = gap*(ht-2);
					display(Huffman.root,750,5,gc,begin);
				}
			}
		});
        
     }
    	
    public void drawCircle(HuffmanNode root,double x,double y,GraphicsContext gc)
    {
    	gc.strokeOval(x, y, 52, 52);
   		if(root.c<260)
   		{
   			gc.fillText(Integer.toString(root.data),x+10, y+26,10);
   			gc.fillText("("+root.c+")",x+30 , y+26,18);
   		}
   		else
   			gc.fillText(Integer.toString(root.data),x+20, y+26,10);
    }
    protected void display(HuffmanNode root,double x,double y,GraphicsContext gc,double g)
    {
		// TODO Auto-generated method stub
    	drawCircle(root,x,y,gc);
    	if(root.left!=null)
    	{
    		double x0 = x+26;
    		double y0 = y+52;
    		double x1 = x0-g;
    		double y1 = y0+vGap;
    		gc.strokeLine(x0,y0,x1,y1);
    		gc.fillText("0", (x0+x1)/2-5, (y1+y0)/2);
    		display(root.left,x1-26,y1,gc,g/2);
    	}
    	if(root.right!=null)
    	{
//    		gc.strokeLine(x+26, y+52, x+102, y+100);
//    		display(root.right,x+76,y+100,gc);
    		double x0 = x+26;
    		double y0 = y+52;
    		double x1 = x0+g;
    		double y1 = y0+vGap;
    		gc.strokeLine(x0,y0,x1,y1);
    		gc.fillText("1", (x1+x0)/2-5, (y1+y0)/2);
    		//display(root.left,x-76,y+100,gc);
    		display(root.right,x1-26,y1,gc,g/2);
    	}
		
	}
    

	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}