package com.example.hemavathi.mypoolinapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mypoolin.sdk.MypoolinOrder;
import com.mypoolin.sdk.objects.RecipientDetails;
import com.mypoolin.sdk.util.MypoolinConstants;

import java.util.ArrayList;
import java.util.Random;

import static com.mypoolin.sdk.util.MypoolinConstants.P2P;

public class InputDetailActivity extends AppCompatActivity{
    private static final String TAG = "InputDetail";
    public String Character = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private String input;
    public EditText organizerName, organizerMobile, organizerEmail, amount, recipientName, recipientId, recipientMobile, recipientEmail, recipientBankAccountNumber, recipientBankIFSC, description;
    public Button proceed;
    public Activity activity;
    public String name, mobile, email, rName, rId, rMobile, rEmail, rAccountNo, rIFSCCode, desc, amt, randomNumber;
   // public String ID_P2P = "mypoolin_test";
   // public String ID_P2M = "mypoolin_test_p2m";
   // public String KEY_P2M = "BBueqE2tmBZW7SRV2MTbD2EVuP4Izl6Y";
   // public String KEY_P2P = "3bdc97c4071747c2aad1a90912e7f6c8";
    public AccessStringInfoData accessStringInfoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_detail);
        activity = this;

        Intent intent = activity.getIntent();
        if (intent != null) {
            input = intent.getStringExtra("inputType");
            accessStringInfoData = (AccessStringInfoData) intent.getSerializableExtra("accessStringInfoData");
        }
        Log.d(TAG,"test input is:"+ input);

        organizerName = findViewById(R.id.editText1);
        organizerMobile = findViewById(R.id.editText2);
        organizerEmail = findViewById(R.id.editText3);
        amount = findViewById(R.id.editText4);
        recipientName = findViewById(R.id.editText5);
        recipientId = findViewById(R.id.editText6);
        recipientMobile = findViewById(R.id.editText7);
        recipientEmail = findViewById(R.id.editText8);
        recipientBankAccountNumber = findViewById(R.id.editText9);
        recipientBankIFSC = findViewById(R.id.editText10);
        description = findViewById(R.id.editText11);
        proceed = findViewById(R.id.button);

        if (input.equalsIgnoreCase("P2P_Virtual")) {
            recipientBankAccountNumber.setVisibility(View.GONE);
            recipientBankIFSC.setVisibility(View.GONE);
        } else if (input.equalsIgnoreCase("Split")) {
            recipientName.setVisibility(View.GONE);
            recipientId.setVisibility(View.GONE);
            recipientMobile.setVisibility(View.GONE);
            recipientEmail.setVisibility(View.GONE);
            recipientBankAccountNumber.setVisibility(View.GONE);
            recipientBankIFSC.setVisibility(View.GONE);
        } else {
            recipientBankAccountNumber.setVisibility(View.VISIBLE);
            recipientBankIFSC.setVisibility(View.VISIBLE);
        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "on click proceed");
                if (input != null) {
                    if (input.equalsIgnoreCase("P2P_Virtual")) {
                        if (ValidateInputs()) {
                            Log.d(TAG, "CallP2PVirtual");
                            randomNumber = generateRandomChars(Character, 4);
                            Log.d(TAG, "randomNumber" + randomNumber);
                            CallP2PVirtual();
                            return;
                        }
                    } else if (input.contains("P2P_Account")) {
                        Log.d(TAG, "test");
                        if (ValidateInputs()) {
                            randomNumber = generateRandomChars(Character, 4);
                            CallP2PAccount();
                            return;
                        }
                    } else if (input.equalsIgnoreCase("Multi_PAY_P2P")) {
                        if (ValidateInputs()) {
                            randomNumber = generateRandomChars(Character, 4);
                            CallMultiPayP2P();
                            return;
                        }
                    } else if (input.equalsIgnoreCase("P2P_CollectRequest")) {
                        if (ValidateInputs()) {
                            randomNumber = generateRandomChars(Character, 4);
                            CallP2PRequest();
                            return;
                        }
                    } else if (input.equalsIgnoreCase("P2P_UPI")) {
                        if (ValidateInputs()) {
                            randomNumber = generateRandomChars(Character, 4);
                            CallP2PUPIRequest();
                            return;
                        }
                    } else if (input.equalsIgnoreCase("P2M_Pay")) {
                        if (ValidateInputs()) {
                            randomNumber = generateRandomChars(Character, 4);
                            CallP2MPay();
                            return;
                        }
                    } else if (input.equalsIgnoreCase("Split")) {
                        if (ValidateInputs()) {
                            randomNumber = generateRandomChars(Character, 4);
                            CallSplit();
                            return;
                        }
                    }
                }
            }
        });
    }

    private boolean ValidateInputs() {
        name = organizerName.getText().toString();
        mobile = organizerMobile.getText().toString();
        email = organizerEmail.getText().toString();
        amt = (amount.getText().toString());
        rName = recipientName.getText().toString();
        rId = recipientId.getText().toString();
        rMobile = recipientMobile.getText().toString();
        rEmail = recipientEmail.getText().toString();
        rAccountNo = recipientBankAccountNumber.getText().toString();
        rIFSCCode = recipientBankIFSC.getText().toString();
        desc = description.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Name cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (mobile.isEmpty() || mobile.length() < 10) {
            Toast.makeText(getApplicationContext(), "Phone Number cannot be less than 10 digits", Toast.LENGTH_LONG).show();
            return false;
        } else if (email.isEmpty() && !email.contains("@")) {
            Toast.makeText(getApplicationContext(), "Invalid Email Id ", Toast.LENGTH_LONG).show();
            return false;
        } else if (amt.isEmpty() && Integer.parseInt(amt) < 1) {
            Toast.makeText(getApplicationContext(), "minimum amount is 2", Toast.LENGTH_LONG).show();
            return false;
        } else if (rName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Recipent name cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (rId.isEmpty() && !rId.contains("@")) {
            Toast.makeText(getApplicationContext(), "Invalid Recipent ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (rMobile.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Recipent phone number cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (rEmail.isEmpty() && !rEmail.contains("@")) {
            Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_LONG).show();
            return false;
        } else if (desc.isEmpty()) {
            Toast.makeText(getApplicationContext(), "description cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(input.equalsIgnoreCase("P2P_Account")){
             if (rIFSCCode.isEmpty()) {
                Toast.makeText(getApplicationContext(), "IFSC Field cannot be empty", Toast.LENGTH_LONG).show();
                return false;
            } else if (rAccountNo.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Account Number cannot be empty", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private void CallP2PVirtual() {
        Log.d(TAG,"CallP2PVirtual"+randomNumber);
        Intent intent = new Intent(activity, MypoolinOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantKey", accessStringInfoData.getP2P_KEY());//req
        bundle.putString("merchantId", accessStringInfoData.getP2P_ID());//req
        bundle.putString("orderId", randomNumber);//req & should be unique for each transaction without special chars
        bundle.putString("description", desc);//req
        bundle.putString("organizerName", name);//req
        bundle.putString("organizerMobile", mobile); //req
        bundle.putString("organizerEmail", email); //req
        bundle.putString("amount", amt); // int VALUE MIN 2 || MAX 100000 //req
        bundle.putString("paymentType", MypoolinConstants.P2P);//req
        bundle.putString("recipientName", rName);//req
        bundle.putString("recipientId", rId);//req
        bundle.putString("recipientMobile", rMobile);//req
        bundle.putString("recipientEmail", rEmail); //req
        bundle.putString("recipientBankAccountNumber", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankIFSC", "");  //Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankMMID", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientAadharNo", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("requestType", MypoolinConstants.PAY);//req
//NOTE : type of payment PAY / REQUEST / UPI_REQUEST
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/ic_launcher");
        bundle.putString("brandURI", uri.toString()); // for your brand image at SDK loader
      bundle2string(bundle);
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);
    }

    private void CallP2PAccount() {
        Log.d(TAG,"CallP2PAccount");
        Intent intent = new Intent(activity, MypoolinOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantKey", accessStringInfoData.getP2P_KEY());//req
        bundle.putString("merchantId", accessStringInfoData.getP2P_ID());//req
        bundle.putString("orderId", randomNumber);//req & should be unique for each transaction without special chars
        bundle.putString("description", desc);//req
        bundle.putString("organizerName", name);//req
        bundle.putString("organizerMobile", mobile); //req
        bundle.putString("organizerEmail", email); //req
        bundle.putString("amount", amt); // int VALUE MIN 2 || MAX 100000 //req
        bundle.putString("paymentType", MypoolinConstants.P2P);//req
        bundle.putString("recipientName", rName);//req
        bundle.putString("recipientId", rId);//req
        bundle.putString("recipientMobile", rMobile);//req
        bundle.putString("recipientEmail", rEmail); //req
        bundle.putString("recipientBankAccountNumber", rAccountNo);//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankIFSC", rIFSCCode);  //Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankMMID", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientAadharNo", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("requestType", MypoolinConstants.PAY);//req
//NOTE : type of payment PAY / REQUEST / UPI_REQUEST
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/ic_launcher");
        bundle.putString("brandURI", uri.toString()); // for your brand image at SDK loader
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);
    }

    private void CallMultiPayP2P() {
        ArrayList<RecipientDetails> recipientsDetail = new ArrayList<>();
        RecipientDetails recipient1 = new RecipientDetails();
        recipient1.setAccountNumber(rAccountNo);
        recipient1.setIfsc(rIFSCCode);
        recipient1.setAmount(amt);
        recipientsDetail.add(recipient1);

        Intent intent = new Intent(activity, MypoolinOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantKey", accessStringInfoData.getP2P_KEY());//req
        bundle.putString("merchantId", accessStringInfoData.getP2P_ID());//req
        bundle.putString("orderId",randomNumber);//req & should be unique for each transaction without special chars
        bundle.putString("description", desc);//req
        bundle.putString("organizerName", name);//req
        bundle.putString("organizerMobile", mobile); //req
        bundle.putString("organizerEmail", email); //req
        bundle.putString("amount", amt); // int VALUE MIN 2 || MAX 100000 //req
        bundle.putString("paymentType", MypoolinConstants.P2P);//req
        bundle.putString("recipientName", rName);//req
        bundle.putString("recipientId", rId);//req
        bundle.putString("recipientMobile", rMobile);//req
        bundle.putString("recipientEmail", rEmail); //req
        bundle.putString("recipientBankAccountNumber", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankIFSC", "");  //Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankMMID", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientAadharNo", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("requestType", MypoolinConstants.PAY);//req
//NOTE : type of payment PAY / REQUEST / UPI_REQUEST
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/ic_launcher");
        bundle.putString("brandURI", uri.toString()); // for your brand image at SDK loader
        bundle.putSerializable("recipientsDetails", recipientsDetail);
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);

    }

    private void CallP2PRequest() {
        Intent intent = new Intent(activity, MypoolinOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantKey", accessStringInfoData.getP2P_KEY());//req
        bundle.putString("merchantId", accessStringInfoData.getP2P_ID());//req
        bundle.putString("orderId",randomNumber);//req & should be unique for each transaction without special chars
        bundle.putString("description", desc);//req
        bundle.putString("organizerName", name);//req
        bundle.putString("organizerMobile", mobile); //req
        bundle.putString("organizerEmail", email); //req
        bundle.putString("amount", amt); // int VALUE MIN 2 || MAX 100000 //req
        bundle.putString("paymentType", MypoolinConstants.P2P);//req
        bundle.putString("recipientName", rName);//req
        bundle.putString("recipientId", rId);//req
        bundle.putString("recipientMobile", rMobile);//req
        bundle.putString("recipientEmail", rEmail); //req
        bundle.putString("recipientBankAccountNumber", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankIFSC", "");  //Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankMMID", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientAadharNo", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("requestType", MypoolinConstants.REQUEST);//req
//NOTE : type of payment PAY / REQUEST / UPI_REQUEST
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/ic_launcher");
        bundle.putString("brandURI", uri.toString()); // for your brand image at SDK loader
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);

    }

    private void CallP2PUPIRequest() {
        Intent intent = new Intent(activity, MypoolinOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantKey", accessStringInfoData.getP2P_KEY());//req
        bundle.putString("merchantId", accessStringInfoData.getP2P_ID());//req
        bundle.putString("orderId",randomNumber);//req & should be unique for each transaction without special chars
        bundle.putString("description", desc);//req
        bundle.putString("organizerName", name);//req
        bundle.putString("organizerMobile", mobile); //req
        bundle.putString("organizerEmail", email); //req
        bundle.putString("amount", amt); // int VALUE MIN 2 || MAX 100000 //req
        bundle.putString("paymentType", MypoolinConstants.P2P);//req
        bundle.putString("recipientName", rName);//req
        bundle.putString("recipientId", rId);//req
        bundle.putString("recipientMobile", rMobile);//req
        bundle.putString("recipientEmail", rEmail); //req
        bundle.putString("recipientBankAccountNumber", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankIFSC", "");  //Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankMMID", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientAadharNo", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("requestType", MypoolinConstants.UPI_REQUEST);//req
//NOTE : type of payment PAY / REQUEST / UPI_REQUEST
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/ic_launcher");
        bundle.putString("brandURI", uri.toString()); // for your brand image at SDK loader
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);

    }

    private void CallP2MPay() {
        Intent intent = new Intent(activity, MypoolinOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantKey", accessStringInfoData.getP2M_KEY());//req
        bundle.putString("merchantId", accessStringInfoData.getP2M_ID());//req
        bundle.putString("orderId",randomNumber);//req & should be unique for each transaction without special chars
        bundle.putString("description", desc);//req
        bundle.putString("organizerName", name);//req
        bundle.putString("organizerMobile", mobile); //req
        bundle.putString("organizerEmail", email); //req
        bundle.putString("amount", amt); // int VALUE MIN 2 || MAX 100000 //req
        bundle.putString("paymentType", MypoolinConstants.P2M);//req
        bundle.putString("recipientName", rName);//req DO NOT CHANGE FOR P2M
        bundle.putString("recipientId", rId);//req DO NOT CHANGE FOR P2M
        bundle.putString("recipientMobile", rMobile);//req  DO NOT CHANGE FOR P2M
        bundle.putString("recipientEmail", rEmail);//req DO NOT CHANGE FOR P2M
        bundle.putString("recipientBankAccountNumber", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankIFSC", "");  //Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientBankMMID", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("recipientAadharNo", "");//Not for UPI Request
//OPTIONAL IF RECEIPIENT VIRTUAL ADDRESS NOT KNOWN
        bundle.putString("requestType", MypoolinConstants.PAY);//req
//NOTE : type of payment PAY / REQUEST / UPI_REQUEST
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/ic_launcher");
        bundle.putString("brandURI", uri.toString()); // for your brand image at SDK loader
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);

    }

    private void CallSplit() {
        Intent intent = new Intent(activity, MypoolinOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantKey", "");//req
        bundle.putString("merchantId", "");//req
        bundle.putString("orderId", randomNumber);
        bundle.putString("description", desc);
        bundle.putString("organizerName", name);
        bundle.putString("organizerMobile", mobile);
        bundle.putString("organizerEmail", email);
        bundle.putString("amount", amt);
        bundle.putString("paymentType", MypoolinConstants.GROUP);
        bundle.putString("invitees", "");
        bundle.putString("theme", "collect");
        bundle.putString("myShare", "");
        bundle.putString("numPeople", "");
        bundle.putString("splitEqual", "");
        bundle.putString("poolName", "Mypoolin Test Pool");
        bundle.putString("mDeadline", "");
        bundle.putInt("mCategoryCount", 5);
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 6:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(activity, "App: Failed", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (data.getExtras().getBoolean("success")) {
                            data.getExtras().getString("response");
                            Toast.makeText(activity, data.getExtras().getString("response"), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(activity, "App: success", Toast.LENGTH_SHORT).show();

                        Bundle bundle = data.getExtras();
                        String pgMeTrnRefNo = bundle.getString("pgMeTrnRefNo");
                        String orderNo = bundle.getString("orderNo");
                        String txnAmount = bundle.getString("txnAmount");
                        String tranAuthdate = bundle.getString("tranAuthdate");
                        String statusCode = bundle.getString("status");
                        String statusDesc = bundle.getString("statusDesc");
                        String responsecode = bundle.getString("responsecode");
                        String approvalCode = bundle.getString("approvalCode");
                        String payerVA = bundle.getString("payerVA");
                        String npciTxnId = bundle.getString("npciTxnId");
                        String refId = bundle.getString("refId");
                        String payerAccountNo = bundle.getString("payerAccountNo");
                        String payerIfsc = bundle.getString("payerIfsc");
                        String payerAccName = bundle.getString("payerAccName");
                        //String​ ​​orders ​=​ ​bundle.getString("orders");
                        //… other values in bundle
                       bundle2string(bundle);
                    }
                }
                break;
        }
    }

    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "=========================\nBundle{\n";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";\n";
        }
        string += " \n}Bundle\n==================================\n";
        Log.i("UPI", string);
        return string;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activity.finish();
    }
    public static String generateRandomChars(String Character, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(Character.charAt(random.nextInt(Character.length())));
        }
        return sb.toString();
    }
}
