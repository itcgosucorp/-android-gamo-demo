package kh.gosu.cuuam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.game.nsdk.inteface.IGameInitListener;
import com.game.nsdk.inteface.IGameOauthListener;
import com.game.nsdk.inteface.IGamePaymentListener;
import com.game.nsdk.inteface.OnSingleClickListener;
import com.game.nsdk.object.GameItemIAPObject;
import com.game.nsdk.utils.GameConstant;
import com.game.nsdk.utils.GameException;
import com.game.nsdk.utils.GameSDK;
import com.game.nsdk.utils.GameTracking;

public class MainActivity extends AppCompatActivity {

    private Button btnDangNhap;
    private Button btnTTTK;
    private Button btnDSITEM;
    private Button btnTTITEM1;
    private Button btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSDK();

        initView();

    }

    public void setupSDK() {
        GameSDK.sdkInitialize(this, "m932.zfabeqgmenvyllc", new IGameInitListener() {
            @Override
            public void onSuccess() {
                onLogin();
            }

            @Override
            public void onError(GameException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void onLogin() {
        GameSDK.onLogin(new IGameOauthListener() {
            @Override
            public void onLoginSuccess(String UserId, String UserName, String accesstoken) {

                btnDangNhap.setVisibility(View.GONE);
                btnDSITEM.setVisibility(View.VISIBLE);
                btnTTITEM1.setVisibility(View.VISIBLE);
                btnDangXuat.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLogout() {
                btnDangNhap.setVisibility(View.GONE);
                btnDSITEM.setVisibility(View.GONE);
                btnTTITEM1.setVisibility(View.GONE);
                btnDangXuat.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void initView() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                GameSDK.showLogin();
            }
        });


        btnDSITEM = (Button) findViewById(R.id.btnDSITEM);
        btnDSITEM.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                for(int i = 0; i < GameConstant.iap_product_ids.size(); i++){
                    Log.d("TAG_ITEM", GameConstant.iap_product_ids.get(i)+"");
                }
            }
        });

        btnTTITEM1 = (Button) findViewById(R.id.btnTTITEM1);
        btnTTITEM1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                if(GameConstant.iap_product_ids.size() <= 0) return;

                String productID = "product1";
                String mProductName = "Mua gói 100KNB";
                String currencyUnit = "VND";
                String amount = "22000.0";
                String serverID       = "S1";
                String characterID    = "Character_ID";
                String extraInfo    = "Extra_Note";

                GameItemIAPObject gosuItemIAPObject = new GameItemIAPObject(productID, mProductName, currencyUnit, amount, serverID, characterID, extraInfo);

                GameSDK.payment(gosuItemIAPObject, new IGamePaymentListener() {
                    @Override
                    public void onPaymentSuccess(String message) {

                    }

                    @Override
                    public void onPaymentError(String message) {

                    }
                });
            }
        });

        btnDangXuat = (Button) findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                GameSDK.logout();
            }
        });

        btnDangNhap.setVisibility(View.VISIBLE);
        btnDSITEM.setVisibility(View.GONE);
        btnTTITEM1.setVisibility(View.GONE);
        btnDangXuat.setVisibility(View.GONE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GameSDK.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}