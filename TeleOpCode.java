
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

@TeleOp(name="Basic: Linear OpMode2", group="Linear Opmode")

public class TeleOpCode2 extends LinearOpMode {

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
        // step (using the FTC Robot Controller app on the phone).
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
            
            boolean clawButtonClose = gamepad2.left_bumper;
            boolean clawButtonOpen = gamepad2.right_bumper;
            
            boolean basePosition = gamepad2.a;
            boolean firstLevelFront = gamepad2.x;
            boolean secondLevelFront = gamepad2.y;
            boolean thirdLevelFront = gamepad2.b;
            boolean firstLevelBack = gamepad2.dpad_left;
            boolean secondLevelBack = gamepad2.dpad_up;
            boolean thirdLevelBack = gamepad2.dpad_right;
            
            
            
            double frontLeftPower = 0;
            double frontRightPower = 0;
            double backLeftPower = 0;
            double backRightPower = 0;
            
            double armBasePower = 0;
            
            if(y>0){
                backRightPower = 0.6;
                frontLeftPower = 0.58;
                frontRightPower = 0.6;
                backLeftPower = 0.6;
            } else if(y<0){
                backRightPower = -0.6;
                frontLeftPower = -0.58;
                frontRightPower = -0.6;
                backLeftPower = -0.6;
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
            
            if(turn>0){
                backRightPower = -0.6;
                frontLeftPower = 0.6;
                frontRightPower = -0.6;
                backLeftPower = 0.6;
                
            } else if(turn<0){
                backRightPower = 0.6;
                frontLeftPower = -0.6;
                frontRightPower = 0.6;
                backLeftPower = -0.6;
            }
            
            if(clawButtonClose) {
                claw.setPosition(0.85);
            }
            
            if(clawButtonOpen) {
                claw.setPosition(0.60);
            }
            
            if (basePosition){
                basePositionFunction();
            }
            
            if (firstLevelFront){
                rightArm.setPosition(1f);
                leftArm.setPosition(0f);
                sleep(200);
                //clawSpin.setPosition(0.72);
                armBase.setPower(-0.80f*powerScale);
                sleep(520);
                armBase.setPower(-0.66f*powerScale);
                sleep(300);
                rightArm.setPosition(0.80f);
                leftArm.setPosition(0.20f);
                armBase.setPower(-0.66f*powerScale);
                sleep(2000);
                basePositionFunction();
                armBase.setPower(-0.2f*powerScale);
                sleep(1000);
                armBase.setPower(0f*powerScale);
                sleep(2000);
            }
            
            if (secondLevelFront){
                rightArm.setPosition(1f);
                leftArm.setPosition(0f);
                sleep(200);
                //clawSpin.setPosition(0.72);
                armBase.setPower(-0.85f*powerScale);
                sleep(690);
                armBase.setPower(-0.72f*powerScale);
                sleep(300);
                rightArm.setPosition(0.4f);
                leftArm.setPosition(0.6f);
                armBase.setPower(-0.72*powerScale);
                sleep(2000);
                basePositionFunction();
                armBase.setPower(-0.2f*powerScale);
                sleep(1000);
                armBase.setPower(0f*powerScale);
                sleep(2000);
            }
            
            if (thirdLevelFront){
                rightArm.setPosition(0.4);
                leftArm.setPosition(0.6);
                //clawSpin.setPosition(0.72);
                //armBase.setPower(-1f*powerScale);
                //sleep(1300);
                //armBase.setPower(0f*powerScale);
                //sleep(3000);
            }
            
            if (firstLevelBack){
                rightArm.setPosition(0.9);
                leftArm.setPosition(0.1);
                //clawSpin.setPosition(0.07);
                //armBase.setPower(-1f*powerScale);
                //sleep(1500);
                //armBase.setPower(0.5*powerScale);
                //sleep(2000);
                //armBase.setPower(0f*powerScale);
                //sleep(2000);
            }
            
            if (secondLevelBack){
                rightArm.setPosition(0.5);
                leftArm.setPosition(0.5);
                clawSpin.setPosition(0.07);
            }
            
            if (thirdLevelBack){
                rightArm.setPosition(0.4);
                leftArm.setPosition(0.4);
                clawSpin.setPosition(0.07);
            }
            
            
            frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            
            armBase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            
            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);
            
            //armBase.setPower(armBasePower);

            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
        
    }
}
