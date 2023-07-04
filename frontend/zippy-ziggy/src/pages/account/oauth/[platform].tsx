import { useEffect } from 'react';
import { useRouter } from 'next/router';
import { useAppDispatch } from '@/hooks/reduxHook';
import { setIsLogin, setNickname, setProfileImg, setUserUuid } from '@/core/user/userSlice';
import { getGoogleAPI, getKakaoAPI } from '@/core/user/userAPI';
import LottieAnimation from '@/components/LottieFiles/LoadingA';

interface SocialSignUpDataResponseDto {
  name: string;
  platform: string;
  platformId: string;
  profileImg: string;
  userUuid?: string;
  nickname?: string;
}

interface KakaoApiResult {
  result?: 'SUCCESS_SIGNUP' | 'SUCCESS_LOGIN';
  nickname?: string;
  profileImg?: string;
  userUuid?: string;
  socialSignUpDataResponseDto?: SocialSignUpDataResponseDto;
  // 반환되는 값에 따라 필드를 추가할 수 있음
}

// 우리 서버로 카카오 로그인 요청을 보냄
function KakaoLoginRedirect() {
  const dispatch = useAppDispatch();

  const router = useRouter();
  const { code } = router.query;
  const { platform } = router.query;
  const token = Array.isArray(code) ? code[0] : code;

  const HandleGetKakaoAPI = async () => {
    const result: KakaoApiResult = await getKakaoAPI(token);
    if (result?.result === 'SUCCESS_SIGNUP') {
      const { name, platform, platformId, profileImg } = result?.socialSignUpDataResponseDto ?? {};
      router.push({
        pathname: '/account/signup',
        query: { name, platform, platformId, profileImg },
      });
    }
    if (result?.result === 'SUCCESS_LOGIN') {
      const { nickname, profileImg, userUuid } = result;
      dispatch(setIsLogin(true));
      dispatch(setNickname(nickname));
      dispatch(setProfileImg(profileImg));
      dispatch(setUserUuid(userUuid));
      router.push({
        pathname: '/',
      });
    }
  };

  // 우리 서버로 구글 로그인 요청을 보냄
  const HandleGetGoogleAPI = async () => {
    const result: KakaoApiResult = await getGoogleAPI(token);
    if (result?.result === 'SUCCESS_SIGNUP') {
      const { name, platform, platformId, profileImg } = result?.socialSignUpDataResponseDto ?? {};
      router.push({
        pathname: '/account/signup',
        query: { name, platform, platformId, profileImg },
      });
    }
    if (result?.result === 'SUCCESS_LOGIN') {
      const { nickname, profileImg, userUuid } = result;
      dispatch(setIsLogin(true));
      dispatch(setNickname(nickname));
      dispatch(setProfileImg(profileImg));
      dispatch(setUserUuid(userUuid));
      router.push({
        pathname: '/',
      });
    }
  };

  useEffect(() => {
    if (token !== undefined && platform !== undefined) {
      if (platform === 'kakao') {
        HandleGetKakaoAPI();
      }
      if (platform === 'google') {
        HandleGetGoogleAPI();
      }
    }
  }, [token, platform]);

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <div style={{ width: '200px' }}>
        <LottieAnimation />
      </div>
    </div>
  );
}

export default KakaoLoginRedirect;
