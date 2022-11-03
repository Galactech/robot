package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Linear OpMode3", group="Linear Opmode")

public class TeleOpCode3 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    
    private DcMotor armBase = null;
    
    private Servo claw = null;
    private Servo clawSpin = null;
    private Servo leftArm = null;
    private Servo rightArm = null;
    
    private float powerScale = 1;
    
    public void basePositionFunction() {
        rightArm.setPosition(0.75);
        leftArm.setPosition(0.25);
        clawSpin.setPosition(0.72);
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone)
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "Frontleft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "Frontright");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "Backleft");
        backRightDrive = hardwareMap.get(DcMotor.class, "Backright");
        
        armBase  = hardwareMap.get(DcMotor.class, "armbase");
        
        claw  = hardwareMap.get(Servo.class, "claw");
        clawSpin = hardwareMap.get(Servo.class, "clawspin");
        leftArm  = hardwareMap.get(Servo.class, "leftarm");
        rightArm = hardwareMap.get(Servo.class, "rightarm");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double y = -gamepad1.left_stick_y;
            //double x = -gamepad1.left_stick_x;
            double turn  =  gamepad1.right_stick_x;
            double armMovement = -gamepad2.left_stick_y;
            double armHold = -gamepad2.right_stick_y;
            
            boolean clawButtonClose = gamepad2.right_bumper;
            boolean clawButtonOpen = gamepad2.left_bumper;

            boolean elbowCompact = gamepad2.dpad_down;
            boolean elbowRest = gamepad2.a;
            boolean elbowFirstLevelFront = gamepad2.x;
            boolean elbowSecondLevelFront = gamepad2.y;
            boolean elbowThirdLevelFront = gamepad2.b;
            boolean elbowThirdLevelBack = gamepad2.dpad_up;
            boolean elbowSecondLevelBack = gamepad2.dpad_right;
            
            boolean slightForward = gamepad1.dpad_up;
            boolean slightBackward = gamepad1.dpad_down;
            boolean slightRight = gamepad1.dpad_right;
            boolean slightLeft = gamepad1.dpad_left;
            
            double slightRightTurn = gamepad1.right_trigger;
            double slightLeftTurn = gamepad1.left_trigger;
            
            double frontLeftPower = 0;
            double frontRightPower = 0;
            double backLeftPower = 0;
            double backRightPower = 0;

            double armPower = 0;
            double armBasePower = 0;
            
            if(y>0){
                backRightPower = 0.6;
                frontLeftPower = 0.59;
                frontRightPower = 0.6;
                backLeftPower = 0.6;
            } else if(y<0){
                backRightPower = -0.6;
                frontLeftPower = -0.59;
                frontRightPower = -0.6;
                backLeftPower = -0.6;
            } 
            
            if(turn>0){
                backRightPower = -0.3;
                frontLeftPower = 0.3;
                frontRightPower = -0.3;
                backLeftPower = 0.3;
            } else if(turn<0){
                backRightPower = 0.3;
                frontLeftPower = -0.3;
                frontRightPower = 0.3;
                backLeftPower = -0.3;
            } 
            
            if(slightForward){
                backRightPower = 0.3;
                frontLeftPower = 0.3;
                frontRightPower = 0.3;
                backLeftPower = 0.3;
            } else if(slightBackward){
                backRightPower = -0.3;
                frontLeftPower = -0.3;
                frontRightPower = -0.3;
                backLeftPower = -0.3;
            } 
            
            if(slightRight){
                backRightPower = 0.4;
                frontLeftPower = 0.4;
                frontRightPower = -0.4;
                backLeftPower = -0.4;
            } else if(slightLeft){
                backRightPower = -0.4;
                frontLeftPower = -0.4;
                frontRightPower = 0.4;
                backLeftPower = 0.4;
            } 
            
            if(gamepad1.x){
                backRightPower = -0.6;
                frontLeftPower = -0.6;
                frontRightPower = 0.6;
                backLeftPower = 0.6;
            }
            
            if(gamepad1.b){
                backRightPower = 0.6;
                frontLeftPower = 0.6;
                frontRightPower = -0.6;
                backLeftPower = -0.6;
            }
            
            if(slightRightTurn>0){
                backRightPower = -0.5;
                frontLeftPower = 0.5;
                frontRightPower = -0.5;
                backLeftPower = 0.5;
                
            } else if(slightLeftTurn>0){
                backRightPower = 0.5;
                frontLeftPower = -0.5;
                frontRightPower = 0.5;
                backLeftPower = -0.5;
            }
            
            if(clawButtonClose) {
                claw.setPosition(0.90);
            }
            
            if(clawButtonOpen) {
                claw.setPosition(0.60);
            }
            
            if (armMovement > 0){
                armBasePower = -0.74;
                if (armHold > 0) {
                    armBasePower = -0.6;
                }
            } else if(armMovement < 0) {
                armBasePower = 0.5;
            }else{
                armBasePower = -0.2;
            }

            if (elbowCompact){
                rightArm.setPosition(1);
                leftArm.setPosition(0);
            }

            if (elbowRest){
                rightArm.setPosition(0.78);
                leftArm.setPosition(0.22);
                clawSpin.setPosition(0.07);
            }
            
            if (elbowFirstLevelFront){
                rightArm.setPosition(0.80);
                leftArm.setPosition(0.20);
                clawSpin.setPosition(0.07);
            }
            
            if (elbowSecondLevelFront){
                rightArm.setPosition(0.45);
                leftArm.setPosition(0.55);
                clawSpin.setPosition(0.07);
            }
            
            if (elbowThirdLevelFront){
                rightArm.setPosition(0.25);
                leftArm.setPosition(0.75);
                clawSpin.setPosition(0.07);
            }
            
            if (elbowThirdLevelBack){
                clawSpin.setPosition(0.72);
                rightArm.setPosition(0.65);
                leftArm.setPosition(0.35);
            }
            
            if (elbowSecondLevelBack){
                clawSpin.setPosition(0.72);
                rightArm.setPosition(0.8);
                leftArm.setPosition(0.2);
            }
            
            
            /*if (spinUp){
                clawSpin.setPosition(0.07);
            }

            if (spinDown){
                clawSpin.setPosition(0.72);
            }*/
            
            frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            
            armBase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            
            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);
            
            armBase.setPower(armBasePower);

            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
        
    }
}
