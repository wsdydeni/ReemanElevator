package reeman.elevator.demo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import reeman.elevator.demo.MainActivity;
import reeman.elevator.demo.R;
import reeman.elevator.demo.model.resources.BuildingResult;
import reeman.elevator.demo.model.ErrorMessageResult;
import reeman.elevator.demo.model.OAuth2TokenResult;
import reeman.elevator.demo.utils.ProgressDialogUtils;

/**
 * 登录界面
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    TextInputLayout usernameInputLayout;
    TextInputLayout passwordInputLayout;
    MaterialButton loginButton;

    public static final String KEY_username = "KEY_username";
    public static final String KEY_password = "KEY_password";
    public static final String KEY_AccessToken = "KEY_AccessToken";
    public static final String KEY_scope = "KEY_scope";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameInputLayout = view.findViewById(R.id.login_input_username_layout);
        String username = MMKV.defaultMMKV().getString(KEY_username, "eaabf470-0cc5-46cd-a2bc-da383537b0cc");
        Objects.requireNonNull(usernameInputLayout.getEditText()).setText(username);
        passwordInputLayout = view.findViewById(R.id.login_input_password_layout);
        String password = MMKV.defaultMMKV().getString(KEY_password, "3ae1a0997eea7cc1a0539897297ac6515906885cff75e2aa48a9a285cf4711c6");
        Objects.requireNonNull(passwordInputLayout.getEditText()).setText(password);
        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = usernameInputLayout.getEditText();
                if(editText == null || editText.getEditableText().toString().isEmpty()) {
                    ToastUtils.show("please input your account");
                    return;
                }
                EditText editText1 = passwordInputLayout.getEditText();
                if(editText1 == null || editText1.getEditableText().toString().isEmpty()) {
                    ToastUtils.show("please input your password");
                    return;
                }
                getToken(editText, editText1);
            }
        });
    }

    private final Callback buildingCallback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            ToastUtils.show("get Building Resource failed");
            MainActivity mainActivity = (MainActivity) requireActivity();
            dismissLoading(mainActivity);
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            MainActivity mainActivity = (MainActivity) requireActivity();
            if(response.code() == 200) {
                if(response.body() != null) {
                    dismissLoading(mainActivity);
                    BuildingResult buildingResult = MainActivity.gson.fromJson(response.body().string(), BuildingResult.class);
                    if(buildingResult.getBuildings().isEmpty()) {
                        ToastUtils.show("buildings is empty");
                        return;
                    }
                    mainActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("building", buildingResult);
                            Navigation.findNavController(mainActivity, R.id.nav_host_fragment)
                                    .navigate(R.id.action_loginFragment_to_buildingFragment, bundle);
                        }
                    });
                }
            }else {
                if(response.body() != null) {
                    ErrorMessageResult errorMessageResult = MainActivity.gson.fromJson(response.body().string(), ErrorMessageResult.class);
                    ToastUtils.show(errorMessageResult.getMessage());
                    dismissLoading(mainActivity);
                }
            }
        }
    };

    private void getToken(EditText editText, EditText editText1) {
        ProgressDialogUtils.showProgressDialog(requireContext(), "loading");
        MainActivity mainActivity = (MainActivity) requireActivity();
        String username = editText.getText().toString();
        String password = editText1.getText().toString();
        if(mainActivity.websocketService != null) {
            Callback getTokenCallback = new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    ToastUtils.show("getToken failed");
                    dismissLoading(mainActivity);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.code() == 200) {
                        if (response.body() != null) {
                            OAuth2TokenResult oAuth2TokenResult = MainActivity.gson.fromJson(response.body().string(), OAuth2TokenResult.class);
                            MMKV.defaultMMKV().putString(KEY_username, username);
                            MMKV.defaultMMKV().putString(KEY_password, password);
                            MMKV.defaultMMKV().putString(KEY_AccessToken, oAuth2TokenResult.getAccessToken());
                            MMKV.defaultMMKV().putString(KEY_scope, oAuth2TokenResult.getScope());
                            mainActivity.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String token = MMKV.defaultMMKV().getString(KEY_AccessToken, "");
                                    Log.d("test", "token: " + token);
                                    mainActivity.websocketService.getListResources(token, buildingCallback);
                                }
                            });
                        }
                    }else {
                        if(response.body() != null) {
                            ErrorMessageResult errorMessageResult = MainActivity.gson.fromJson(response.body().string(), ErrorMessageResult.class);
                            ToastUtils.show(errorMessageResult.getMessage());
                            dismissLoading(mainActivity);
                        }
                    }
                }
            };
            mainActivity.websocketService.getToken(username, password, getTokenCallback);
        }
    }

    private static void dismissLoading(MainActivity mainActivity) {
        mainActivity.handler.post(ProgressDialogUtils::hideProgressDialog);
    }
}
