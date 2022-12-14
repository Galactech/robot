/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2022-2023 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine which image is being presented to the robot.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */

@Autonomous(name = "AutoLeft")

public class AutoLeft extends LinearOpMode {
    
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

    /*
     * Specify the source for the Tensor Flow Model.
     * If the TensorFlowLite object model is included in the Robot Controller App as an "asset",
     * the OpMode must to load it using loadModelFromAsset().  However, if a team generated model
     * has been downloaded to the Robot Controller's SD FLASH memory, it must to be loaded using loadModelFromFile()
     * Here we assume it's an Asset.    Also see method initTfod() below .
     */
    private static final String TFOD_MODEL_FILE = "model_20221104_233054.tflite";
    // private static final String TFOD_MODEL_FILE  = "/sdcard/FIRST/tflitemodels/CustomTeamModel.tflite";


    private static final String[] LABELS = {
            "purpleTag",
            "greenTag",
            "whiteTag"
    };

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "ATzl1fr/////AAABmfX4/a94rUj3ouzIGP3Vqxk9yGyZvy+HYxTFoqsMbAxX1+CIwLU2+refuksPU/5L2otwxB3PqLFRGVqklWHqIJtthvU74FI6EZnXyxEjS2fZRLBAG2ZIndEUvoisTiNs1dPi5MlM3GKIx38EA9l7cer8adOKBWHN/ER1TZSO/J6neFPGLPkCGJUCIVHsOzU43JujKmT1owQ1c1NBX3h3FCUYzw1EXG2wY8DVS37sxYrse6tb2xI6oLfbuNZuNM0B6rG85uMTclewXBiERZytNQkcUt7ZBTnHCyrkQaRmTqXfIidccXn63paKwGEuIceCdLzPxhHZ5lf8KoXiuzFHcjfqqkRHhJdI3BDMDrnyyLOh";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

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
        
        claw.setPosition(0.85);
        rightArm.setPosition(1);
        leftArm.setPosition(0);
        clawSpin.setPosition(0.07);
        
        frontLeftPos = 0;
        frontRightPos = 0;
        backLeftPos = 0;
        backRightPos = 0;
        armPos = 0;
        
        telemetry.addData("test", System.currentTimeMillis());
        
        double armBasePower = 0;
        
        waitForStart();
        
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.0, 16.0/9.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            long start = System.currentTimeMillis();
            long end = start + 5 * 1000;
            telemetry.addData("End", end);
            telemetry.update();
            boolean exitFromMainLoop = false;
            while (opModeIsActive()) {
                if (System.currentTimeMillis() > end) {
                    telemetry.addData("work", "working");
                    drive(70, 70, 70, 70, 0.25);
                    sleep(500);
                    drive(-220, 220, 220, -220, 0.25);
                    sleep(500);
                    rightArm.setPosition(0.85);
                    leftArm.setPosition(0.15);
                    clawSpin.setPosition(0.07);
                    sleep(1000);
                    claw.setPosition(0.6);
                    //back to original position
                    rightArm.setPosition(1);
                    leftArm.setPosition(0);
                    sleep(500);
                    drive(220, -220, -220, 220, 0.25);
                    sleep(500);
                    drive(-70, -70, -70, -70, 0.25);
                    drive(10, 10, 10, 10, 0.25);
                    sleep(300);
                    drive(40, -40, -40, 40, 0.25);
                    sleep(700);
                    drive(570, 570, 570, 570, 0.25);
                    /*armBasePower = -0.5;
                    sleep(400);
                    armBasePower = 0;
                    rightArm.setPosition(0.78);
                    leftArm.setPosition(0.22);*/
                    sleep(1000);
                    telemetry.update();
                    break;
                }
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        
                        telemetry.addData("# Objects Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display image position/size information for each one
                        // Note: "Image number" refers to the randomized image orientation/number
                        for (Recognition recognition : updatedRecognitions) {
                            double col = (recognition.getLeft() + recognition.getRight()) / 2 ;
                            double row = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                            double width  = Math.abs(recognition.getRight() - recognition.getLeft()) ;
                            double height = Math.abs(recognition.getTop()  - recognition.getBottom()) ;

                            telemetry.addData(""," ");
                            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100 );
                            telemetry.addData("- Position (Row/Col)","%.0f / %.0f", row, col);
                            telemetry.addData("- Size (Width/Height)","%.0f / %.0f", width, height);
                            
                            //first drop
                            drive(70, 70, 70, 70, 0.25);
                            sleep(500);
                            drive(-220, 220, 220, -220, 0.25);
                            sleep(500);
                            rightArm.setPosition(0.85);
                            leftArm.setPosition(0.15);
                            clawSpin.setPosition(0.07);
                            sleep(1000);
                            claw.setPosition(0.6);
                            //back to original position
                            rightArm.setPosition(1);
                            leftArm.setPosition(0);
                            sleep(500);
                            drive(220, -220, -220, 220, 0.25);
                            sleep(500);
                            drive(-70, -70, -70, -70, 0.25);
                            //parking
                            if (recognition.getLabel().equals("whiteTag")) {
                                drive(1, 1, 1, 1, 0.25);
                                sleep(500);
                                drive(680, -680, -680, 680, 0.2);
                                sleep(1000);
                                drive(590, 590, 590, 590, 0.25);
                                sleep(500);
                                /*rightArm.setPosition(0.78);
                                leftArm.setPosition(0.22);*/
                                sleep(500);
                                exitFromMainLoop = true;
                                break;
                            }
                            
                            if (recognition.getLabel().equals("greenTag")) {
                                drive(1, 1, 1, 1, 0.25);
                                sleep(500);
                                drive(-540, 540, 540, -540, 0.2);
                                sleep(800);
                                drive(605, 605, 605, 605, 0.25);
                                sleep(500);
                                /*rightArm.setPosition(0.78);
                                leftArm.setPosition(0.22);*/
                                sleep(500);
                                exitFromMainLoop = true;
                                break;
                            }
                            
                            if (recognition.getLabel().equals("purpleTag")) {
                                drive(10, 10, 10, 10, 0.25);
                                sleep(300);
                                drive(40, -40, -40, 40, 0.25);
                                sleep(700);
                                drive(570, 570, 570, 570, 0.25);
                                /*armBasePower = -0.5;
                                sleep(400);
                                armBasePower = 0;
                                rightArm.setPosition(0.78);
                                leftArm.setPosition(0.22);*/
                                sleep(1000);
                                exitFromMainLoop = true;
                                break;
                            }
                        telemetry.update();    
                        armBase.setPower(armBasePower);
                        break;
                        }
                        if (exitFromMainLoop) {
                            break;
                        }
                    } 
                }
            }
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.6f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        //tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
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
}
