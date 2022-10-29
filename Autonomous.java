package org.firstinspires.ftc.teamcode.official;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Auto")

public class Auto extends LinearOpMode{
    
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    
    private DcMotor armBase = null;
    
    private Servo claw = null;
    private Servo clawSpin = null;
    private Servo leftArm = null;
    private Servo rightArm = null;
    
    private int frontLeftPos;
    private int frontRightPos;
    private int backLeftPos;
    private int backRightPos;
    
    private int armPos;
    
    private ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public void runOpMode() {
        
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "Frontleft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "Frontright");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "Backleft");
        backRightDrive = hardwareMap.get(DcMotor.class, "Backright");
        
        armBase  = hardwareMap.get(DcMotor.class, "armbase");
        
        claw  = hardwareMap.get(Servo.class, "claw");
        clawSpin = hardwareMap.get(Servo.class, "clawspin");
        leftArm  = hardwareMap.get(Servo.class, "leftarm");
        rightArm = hardwareMap.get(Servo.class, "rightarm");
        
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        
        frontLeftPos = 0;
        frontRightPos = 0;
        backLeftPos = 0;
        backRightPos = 0;
        armPos = 0;
        
        waitForStart();
        
        claw.setPosition(0.90);
        rightArm.setPosition(1);
        leftArm.setPosition(0);
        sleep(200);
        /*drive(15, 15, 15, 15, 0.4);
        drive(325, -325, -325, 325, 0.4);
        sleep(500);
        drive(350, 350, 350, 350, 0.7);
        drive(-850, 850, -850, 850, 0.3);
        drive(200, 200, 200, 200, 0.4);
        drive(0, 0, 0, 0, 0);
        sleep(200);*/
        //drive(200, 200, 200, 200, 0.4);
        /*claw.setPosition(0.90);
        sleep(1000);
        rightArm.setPosition(1);
        leftArm.setPosition(0);
        sleep(100);*/
        armMovement(-60, 0.75);
        armMovement(-200, 0.70);
        armMovement(-150, 0.5);
        clawSpin.setPosition(0.72);
        sleep(1000);
        //armMovement(-100, 0.5);
        rightArm.setPosition(0.58);
        leftArm.setPosition(0.42);
        sleep(2000);
        claw.setPosition(0.60);
        sleep(1000);
        rightArm.setPosition(1);
        leftArm.setPosition(0);
        sleep(1000);
        armMovement(150, 0.7);
        armMovement(80, 0.4);
        armMovement(-200, 0.3);
        rightArm.setPosition(0.73);
        leftArm.setPosition(0.27);
        sleep(1000);
    }
    
    private void drive(int frontLeftTarget, int frontRightTarget, int backLeftTarget, int backRightTarget, double speed) {
        frontLeftPos += frontLeftTarget;
        frontRightPos += frontRightTarget;
        backLeftPos += backLeftTarget;
        backRightPos += backRightTarget;
        
        frontLeftDrive.setTargetPosition(frontLeftPos);
        frontRightDrive.setTargetPosition(frontRightPos);
        backLeftDrive.setTargetPosition(backLeftPos);
        backRightDrive.setTargetPosition(backRightPos);
        
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        frontLeftDrive.setPower(speed);
        frontRightDrive.setPower(speed);
        backLeftDrive.setPower(speed);
        backRightDrive.setPower(speed);
        
        while(opModeIsActive() && frontLeftDrive.isBusy() && frontRightDrive.isBusy() && backLeftDrive.isBusy() && backRightDrive.isBusy()) {
            idle();
        }
    }
    
    private void armMovement(int armTarget, double speed) {
        armPos += armTarget;
        
        armBase.setTargetPosition(armPos);
        
        armBase.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        armBase.setPower(speed);
        
        while(opModeIsActive() && armBase.isBusy()) {
            idle();
        }
    }
    
    private void runCode() {
        rightArm.setPosition(1);
        leftArm.setPosition(0);
        sleep(200);
        drive(400, -400, -400, 400, 0.4);
        drive(400, 400, 400, 400, 0.4);
        drive(200, -200, 200, -200, 0.2);
        sleep(200);
        //drive(200, 200, 200, 200, 0.4);
        /*claw.setPosition(0.90);
        sleep(1000);
        rightArm.setPosition(1);
        leftArm.setPosition(0);
        sleep(100);*/
        armMovement(-50, 0.75);
        armMovement(-150, 0.7);
        armMovement(-150, 0.5);
        clawSpin.setPosition(0.72);
        sleep(2000);
        //armMovement(-100, 0.5);
        rightArm.setPosition(0.58);
        leftArm.setPosition(0.42);
        sleep(2500);
        claw.setPosition(0.60);
        sleep(1500);
        rightArm.setPosition(1);
        leftArm.setPosition(0);
        sleep(1000);
        armMovement(150, 0.7);
        armMovement(-100, 0.5);
        armMovement(-200, 0.3);
        sleep(1500);
        rightArm.setPosition(0.73);
        leftArm.setPosition(0.27);
        sleep(1000);
    }
}