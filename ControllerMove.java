/* Copyright (c) 2017 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Controller Move", group="2022")

public class ControllerMove extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorTopRight = null;
    private DcMotor motorBackRight = null;
    private DcMotor motorTopLeft = null;
    private DcMotor motorBackLeft = null;
    private DcMotor motorWheelRight = null;
    private DcMotor motorWheelLeft = null;
    private Servo servoIntakeRotate = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorTopRight = hardwareMap.get(DcMotor.class, "Motor 1");
        motorBackRight = hardwareMap.get(DcMotor.class, "Motor 2");
        motorTopLeft = hardwareMap.get(DcMotor.class, "Motor 3");
        motorBackLeft = hardwareMap.get(DcMotor.class, "Motor 4");
        
        motorWheelRight = hardwareMap.get(DcMotor.class, "Left Duck");
        motorWheelLeft = hardwareMap.get(DcMotor.class, "Right Duck");
        
        servoIntakeRotate = hardwareMap.get(Servo.class, "Intake Rotate");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            
            double x = -gamepad1.right_stick_x;
            if (x > 0) {
                motorTopRight.setPower(-0.5f);
                motorTopLeft.setPower(-0.5f);
                motorBackRight.setPower(0.5f);
                motorBackLeft.setPower(0.5f);
            } else if (x < 0) {
                motorTopRight.setPower(0.5f);
                motorTopLeft.setPower(0.5f);
                motorBackRight.setPower(-0.5f);
                motorBackLeft.setPower(-0.5f);
            } else {
                motorTopRight.setPower(0f);
                motorTopLeft.setPower(0f);
                motorBackRight.setPower(0f);
                motorBackLeft.setPower(0f);
            }
            
            double y = -gamepad1.left_stick_y;
            if (y > 0) {
                motorTopRight.setPower(0.8f);
                motorTopLeft.setPower(-0.8f);
                motorBackRight.setPower(0.8f);
                motorBackLeft.setPower(-0.8f);
            } else if (y < 0) {
                motorTopRight.setPower(-0.8f);
                motorTopLeft.setPower(0.8f);
                motorBackRight.setPower(-0.8f);
                motorBackLeft.setPower(0.8f);
            } else {
                motorTopRight.setPower(0f);
                motorTopLeft.setPower(0f);
                motorBackRight.setPower(0f);
                motorBackLeft.setPower(0f);
            }
            
            if (gamepad1.a) {
                motorWheelLeft.setPower(1f);
            } else {
                motorWheelLeft.setPower(0f);
            }
            
            if (gamepad1.b) {
                motorWheelRight.setPower(1f);
            } else {
                motorWheelRight.setPower(0f);
            }
            
            if (gamepad1.x) {
                servoIntakeRotate.setPosition(0.5f);
            } else {
                servoIntakeRotate.setPosition(0.75f);
            }
            
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
