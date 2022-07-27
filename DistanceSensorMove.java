package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Distance Sensor Move", group="2022")

public class DistanceSensorMove extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorTopRight = null;
    private DcMotor motorBackRight = null;
    private DcMotor motorTopLeft = null;
    private DcMotor motorBackLeft = null;
    private DistanceSensor distanceFront = null;
    private DistanceSensor distanceBack = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "init");
        telemetry.update();

        motorTopRight = hardwareMap.get(DcMotor.class, "Motor 1");
        motorBackRight = hardwareMap.get(DcMotor.class, "Motor 2");
        motorTopLeft = hardwareMap.get(DcMotor.class, "Motor 3");
        motorBackLeft = hardwareMap.get(DcMotor.class, "Motor 4");
        distanceFront = hardwareMap.get(DistanceSensor.class, "Front distance");
        distanceBack = hardwareMap.get(DistanceSensor.class, "Back distance");
        
        waitForStart();
        runtime.reset();
        
        float powerScale = 1;
        String direction = "forward";
        
        motorTopRight.setPower(.1f*powerScale);
        motorTopLeft.setPower(-.1f*powerScale);
        motorBackRight.setPower(.1f*powerScale);
        motorBackLeft.setPower(-.1f*powerScale);
        while (opModeIsActive()) {
            double distanceFrontMM = distanceFront.getDistance(DistanceUnit.MM);
            double distanceBackMM = distanceBack.getDistance(DistanceUnit.MM);
            if (distanceFrontMM < 500) {
                motorTopRight.setPower(0f*powerScale);
                motorTopLeft.setPower(-0f*powerScale);
                motorBackRight.setPower(0f*powerScale);
                motorBackLeft.setPower(-0f*powerScale);
                sleep(1000);
                motorTopRight.setPower(-.3f*powerScale);
                motorTopLeft.setPower(.3f*powerScale);
                motorBackRight.setPower(-.3f*powerScale);
                motorBackLeft.setPower(.3f*powerScale);
                sleep(1000);
                direction = "backward";
            } else if (distanceBackMM < 500) {
                motorTopRight.setPower(0f*powerScale);
                motorTopLeft.setPower(-0f*powerScale);
                motorBackRight.setPower(0f*powerScale);
                motorBackLeft.setPower(-0f*powerScale);
                sleep(1000);
                motorTopRight.setPower(.3f*powerScale);
                motorTopLeft.setPower(-.3f*powerScale);
                motorBackRight.setPower(.3f*powerScale);
                motorBackLeft.setPower(-.3f*powerScale);
                sleep(1000);
                direction = "forward";
            } else {
                if (direction.equals("forward")) {
                    motorTopRight.setPower(.1f*powerScale);
                    motorTopLeft.setPower(-.1f*powerScale);
                    motorBackRight.setPower(.1f*powerScale);
                    motorBackLeft.setPower(-.1f*powerScale);
                } else {
                    motorTopRight.setPower(-.1f*powerScale);
                    motorTopLeft.setPower(.1f*powerScale);
                    motorBackRight.setPower(-.1f*powerScale);
                    motorBackLeft.setPower(.1f*powerScale);
                }
            }
        }
    }
}
