package com.depromeet.whatnow.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.whatnow.ui.R
import com.depromeet.whatnow.ui.promiseActivate.PromiseActivateViewModel
import com.depromeet.whatnow.ui.theme.WhatNowTheme
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.CircleOverlay
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun WhatNowNaverMap(
    modifier: Modifier,
    onBack: () -> Unit,
    viewModel: PromiseActivateViewModel,
    setUpdateLocationListner: () -> Unit

) {
    val uiState by viewModel.uiState.collectAsState()
    val promise by viewModel.uiState.value.promise.collectAsState()
    val promisesUsersStatusList by viewModel.uiState.value.promisesUsersStatusList.collectAsState()

    val time by viewModel.uiState.value.time.collectAsState()
    val isTimeOver by viewModel.uiState.value.isTimeOver.collectAsState()

    var isPromiseInfo by rememberSaveable { mutableStateOf(true) }

    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 0.0, minZoom = 10.0)
        )
    }

    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = false,
                isLogoClickEnabled = false,
                isZoomControlEnabled = false,
                isRotateGesturesEnabled = false,
                isCompassEnabled = false,
                isScaleBarEnabled = false,
            )
        )
    }

    val seoul = LatLng(promise!!.coordinateVo.latitude, promise!!.coordinateVo.longitude)
    // 카메라 위치에 경우 좌표에 위도에서 - 0.01한 값
    val seoulCamera = LatLng(promise!!.coordinateVo.latitude, promise!!.coordinateVo.longitude)

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(seoulCamera, 11.0)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 284.dp)
    ) {
        NaverMap(
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
        ) {

            /**
             * 도착지 마커
             * */
            Marker(
                icon = OverlayImage.fromResource(R.drawable.map_marker),
                state = MarkerState(position = seoul),
                anchor = Offset(0.5f, 0.5f)
            )

            CircleOverlay(
                center = seoul,
                color = WhatNowTheme.colors.whatNowPurple.copy(alpha = 0.2f),
                outlineColor = WhatNowTheme.colors.whatNowPurple.copy(alpha = 0.2f),
                radius = 3000.0
            )

            promisesUsersStatusList!!.map {
                WhatNowMarkerIcon(
                    promisesUsersStatus = it,
                    promises = promise!!,
                )
            }
        }


        WhatNowNaverMapIconButton(modifier = modifier,
            iconButtonRes = R.drawable.home_icon_button,
            PaddingValues(start = 16.dp, top = 24.dp),
            alignment = Alignment.TopStart,
            color = WhatNowTheme.colors.whatNowBlack,
            tint = Color.White,
            onClick = { onBack() }

        )


        WhatNowNaverMapIconButton(modifier = modifier,
            iconButtonRes = R.drawable.location_icon_button,
            PaddingValues(bottom = 32.dp, end = 16.dp),
            alignment = Alignment.BottomEnd,
            color = WhatNowTheme.colors.whatNowBlack,
            tint = Color.White,
            onClick = {
                setUpdateLocationListner()
            })


        if (isPromiseInfo) {
            if (isTimeOver) {
                WhatNowTimeOverBar(modifier = modifier)

            } else {
                WhatNowNaverMapPromiseInfo(
                    modifier = modifier,
                    promises = promise!!,
                    padding = PaddingValues(top = 24.dp),
                    alignment = Alignment.TopCenter
                )

                WhatNowNaverMapIconButton(modifier = modifier,
                    iconButtonRes = R.drawable.arrow_forward_ios,
                    PaddingValues(end = 16.dp, top = 24.dp),
                    alignment = Alignment.TopEnd,
                    color = WhatNowTheme.colors.whatNowBlack,
                    tint = Color.White,
                    onClick = { isPromiseInfo = false })
            }


        } else {

            Box(
                modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                WhatNowTimePickerPicker(
                    modifier = Modifier,
                    width = 56.dp,
                    height = 56.dp,
                    roundedCornerShape = RoundedCornerShape(20.dp),
                    hour = time.first,
                    min = time.second,
                    style = WhatNowTheme.typography.headline3.copy(
                        fontSize = 28.sp, color = Color.White
                    )
                )
            }

            WhatNowNaverMapIconButton(modifier = modifier,
                iconButtonRes = R.drawable.location,
                PaddingValues(end = 16.dp, top = 24.dp),
                alignment = Alignment.TopEnd,
                color = WhatNowTheme.colors.whatNowBlack,
                tint = Color.White,
                onClick = { isPromiseInfo = true })
        }
    }

}
