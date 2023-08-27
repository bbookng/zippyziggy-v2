import React from 'react';
import { CHAT_GPT_URL } from '@pages/constants';
import t from '@src/chrome/i18n';
import ZippyZiggyLogo from '@pages/content/components/Icons/ZippyZiggyLogo';
import SocialLoginButton from '@pages/content/components/Modal/SocialLoginButton';
import { GoogleIcon, KakaoIcon } from '@pages/content/components/Icons';

const GOOGLE_AUTH_URL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${
  import.meta.env.VITE_GOOGLE_CLIENT_ID
}&redirect_uri=${CHAT_GPT_URL}&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile`;
const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${
  import.meta.env.VITE_KAKAO_CLIENT_ID
}&redirect_uri=${CHAT_GPT_URL}&response_type=code`;

const AuthModalContent = () => {
  const handleAuthLinkClick = (socialName: string) => {
    sessionStorage.setItem('social', socialName);
  };
  return (
    <div id="authModalLinkWrapper">
      <ZippyZiggyLogo />
      <h1>{`${t('signInModal_introduce')}`}</h1>
      <div>
        <SocialLoginButton
          url={KAKAO_AUTH_URL}
          onClick={() => handleAuthLinkClick('kakao')}
          backgroundColor="rgb(255, 255, 22)"
          svg={KakaoIcon()}
        />
        <SocialLoginButton
          url={GOOGLE_AUTH_URL}
          onClick={() => handleAuthLinkClick('google')}
          backgroundColor="rgb(255, 255, 255)"
          svg={GoogleIcon()}
        />
      </div>
    </div>
  );
};

export default AuthModalContent;
