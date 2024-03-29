package ca.teambot.it.cave.examination.bot;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;

public class Robotcontrol extends Fragment {
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashlightOn = false;
    private CameraDevice cameraDevice;
    private TextureView textureView;
    private static final int CAMERA_PERMISSION_REQUEST = 4;
    ImageButton upArrow, rightArrow, downArrow, leftArrow, forwardRight, forwardLeft, backRight, backLeft, flashlight;
    getControls getControls = new getControls();

    public Robotcontrol() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robotcontrol, container, false);

        textureView = view.findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(textureListener);

        upArrow = view.findViewById(R.id.buttonForward);
        rightArrow = view.findViewById(R.id.buttonRight);
        downArrow = view.findViewById(R.id.buttonBack);
        leftArrow = view.findViewById(R.id.buttonLeft);

        forwardRight = view.findViewById(R.id.imageButton_forwardRight);
        forwardLeft = view.findViewById(R.id.imageButton_forwardLeft);
        backRight = view.findViewById(R.id.imageButton_backRight);
        backLeft = view.findViewById(R.id.imageButton_backLeft);

        flashlight = view.findViewById(R.id.imageButton6);


        cameraManager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Use the first camera
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        upArrow.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveForward();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });

        forwardRight.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveForwardRight();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });

        rightArrow.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveRight();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });



        backRight.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveBackRight();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });



        downArrow.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveBack();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });


        backLeft.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveBackLeft();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });

        leftArrow.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveLeft();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });


        forwardLeft.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getControls.moveForwardLeft();
                    return true;
                case MotionEvent.ACTION_UP:
                    getControls.stop();
                    v.performClick();
                    return true;
            }
            return false;
        });


        flashlight.setOnClickListener(view15 -> {
            toggleFlashlight();
        });


        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA))
            {
            }
            else
            {
                ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST );
            }
        }

        return view;
    }

    private final TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            closeCamera();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

        }
    };

    private void openCamera() {
        cameraManager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Use the first camera
            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraManager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    private void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            texture.setDefaultBufferSize(textureView.getWidth(), textureView.getHeight());
            Surface surface = new Surface(texture);

            // Create a capture request builder
            final CaptureRequest.Builder captureRequestBuilder =
                    cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);

            // Create a CameraCaptureSession for camera preview
            cameraDevice.createCaptureSession(Collections.singletonList(surface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            // When the session is ready, start displaying the preview
                            try {
                                cameraCaptureSession.setRepeatingRequest(
                                        captureRequestBuilder.build(),
                                        null,
                                        null
                                );
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                            // Handle configuration failure
                        }
                    },
                    null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void toggleFlashlight() {
        try {
            if (isFlashAvailable()) {
                if (isFlashlightOn) {
                    cameraManager.setTorchMode(cameraId, false);
                    isFlashlightOn = false;
                } else {
                    cameraManager.setTorchMode(cameraId, true);
                    isFlashlightOn = true;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean isFlashAvailable() {
        try {
            // Get the characteristics of the camera with the given ID
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);

            // Check if the flash unit is available
            Boolean flashAvailable = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);

            return flashAvailable != null && flashAvailable;
        } catch (CameraAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

}