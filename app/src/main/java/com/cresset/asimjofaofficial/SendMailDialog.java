package com.cresset.asimjofaofficial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by attaullahkhizar on 9/28/17.
 */

public class SendMailDialog extends DialogFragment {

    private EditText toEmail,subject,message;
    private Button sendEmail;
    //private String emailTo ="abc@gmail.com";;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.send_email_dialog,container,false);

        toEmail = (EditText) view.findViewById(R.id.email_to);
        subject = (EditText) view.findViewById(R.id.email_subject);
        message = (EditText) view.findViewById(R.id.email_message);
        sendEmail = (Button) view.findViewById(R.id.send_email);


        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailTo = toEmail.getText().toString();
                String emailSubject = subject.getText().toString();
                String emailContent = message.getText().toString();


                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, emailTo);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
                /// use below 2 commented lines if need to use BCC an CC feature in email
                //emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{to});
                ////use below 3 commented lines if need to send attachment
//                emailIntent .setType("image/jpeg");
//                emailIntent .putExtra(Intent.EXTRA_SUBJECT, "My Picture");
//                emailIntent .putExtra(Intent.EXTRA_STREAM, Uri.parse("file://sdcard/captureimage.png"));

                //need this to prompts email client only
                emailIntent.setType("text/plain");
                toEmail.setText("abc@gmail.com");

                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });


        return view;
    }

}
