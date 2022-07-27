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

@Autonomous(name="Color Sensor Move", group="2022")

public class ColorSensorMove extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorTopRight = null;
    private DcMotor motorBackRight = null;
    private DcMotor motorTopLeft = null;
    private DcMotor motorBackLeft = null;
    private ColorSensor colorSensor = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "init");
        telemetry.update();

        motorTopRight = hardwareMap.get(DcMotor.class, "Motor 1");
        motorBackRight = hardwareMap.get(DcMotor.class, "Motor 2");
        motorTopLeft = hardwareMap.get(DcMotor.class, "Motor 3");
        motorBackLeft = hardwareMap.get(DcMotor.class, "Motor 4");
        colorSensor = hardwareMap.get(ColorSensor.class, "Color sensor");
        
        waitForStart();
        runtime.reset();
        
        // float powerScale = 1;
        
        // motorTopRight.setPower(.1f*powerScale);
        // motorTopLeft.setPower(-.1f*powerScale);
        // motorBackRight.setPower(.1f*powerScale);
        // motorBackLeft.setPower(-.1f*powerScale);
        while (opModeIsActive()) {
            float hue = JavaUtil.colorToHue(colorSensor.argb());
            telemetry.addData("hue", hue);
            telemetry.update();
        }
    }
}
