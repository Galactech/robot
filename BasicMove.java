package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Basic Move", group="2022")

public class BasicMove extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorTopRight = null;
    private DcMotor motorBackRight = null;
    private DcMotor motorTopLeft = null;
    private DcMotor motorBackLeft = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "init");
        telemetry.update();

        motorTopRight = hardwareMap.get(DcMotor.class, "Motor 1");
        motorBackRight = hardwareMap.get(DcMotor.class, "Motor 2");
        motorTopLeft = hardwareMap.get(DcMotor.class, "Motor 3");
        motorBackLeft = hardwareMap.get(DcMotor.class, "Motor 4");
        
        waitForStart();
        runtime.reset();
        
        float powerScale = 1f;
        
        motorTopRight.setPower(1f*powerScale);
        motorTopLeft.setPower(1f*-1*powerScale);
        motorBackRight.setPower(2f*powerScale);
        motorBackLeft.setPower(2f*-1*powerScale);
        while (opModeIsActive()) {
            
        }
    }
}
