package org.firstinspires.ftc.teamcode.freight_frenzy;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Primus_TeleOp", group = "Linear Opmode")
//@Disabled
public class Primus_TeleOp extends BaseClass_FF {    // LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    double zeroPosition = 0;
    boolean encoderReset = false;
    @Override
    public void runOpMode() {

        defineComponentsPrimus();

        while(armLimit.getVoltage() > 3.0) {

            telemetry.addData("armLimit", armLimit.getVoltage());
            telemetry.addData("arm power", mArm.getPower());
            telemetry.addData("arm position", mBR.getCurrentPosition());
            telemetry.addData("zeroPosition", zeroPosition);
            telemetry.update();
            if (!encoderReset) {
                if (armLimit.getVoltage() < 3.0) {

                    //using mBR encoder port for mArm position
                    mArm.setPower(0);
                    mBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    encoderReset = true;
                    mBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                } else {

                    mArm.setPower(0.5);

                }
            }

        }

        waitForStart();

        while (opModeIsActive()) {


            telemetry.addData("armLimit", armLimit.getVoltage());
            telemetry.addData("arm power", mArm.getPower());
            telemetry.addData("arm position", mBR.getCurrentPosition());
            telemetry.addData("zeroPosition", zeroPosition);
            telemetry.update();


            //Gamepad 1 Variables
            double leftY1 = gamepad1.left_stick_y;
            double rightX1 = gamepad1.right_stick_x;

            //Gamepad 2 Variables
            double leftY2 = gamepad2.left_stick_y;
            double rightTrigger2 = gamepad2.right_trigger;
            double leftTrigger2 = gamepad2.left_trigger;

            //Drivetrain controls
            mBL.setPower(leftY1 - rightX1);
            mBR.setPower(leftY1 + rightX1);
            mFL.setPower(-leftY1 + rightX1);
            mFR.setPower(leftY1 + rightX1);

            //Arm Motor Controls

            //make sure blue wire of the sensor is closest to the 0.1 side (to the outside of the
            //if statement makes sure that arm can only drive up if limit switch is pressed
            //if limit switch is pressed, voltage is around 0, if not pressed, voltage is around 3.3
            if(armLimit.getVoltage() < 3.0){

                if(leftY2 > 0) {
                    mArm.setPower(0);
                }

                else{
                    mArm.setPower(leftY2);
                }
            }else if(mBR.getCurrentPosition() < -9000){

                if(leftY2 < 0) {
                    mArm.setPower(0);
                }

                else{
                    mArm.setPower(leftY2);
                }
            } else{

                mArm.setPower(leftY2);

            }

            if (rightTrigger2 != 0) {
                //sL.setPosition(0.3);
                sR.setPosition(0.8);
            } else {
                sR.setPosition(0.3);
            }
            if (leftTrigger2 != 0) {
                sL.setPosition(0.3);
                //sR.setPosition(0.3);
            } else {
                sL.setPosition(0.8);
            }


        }


    }


}
