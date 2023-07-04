import { FcGoogle } from 'react-icons/fc';
import { RiKakaoTalkFill } from 'react-icons/ri';
import IconButton from '@/components/Button/IconButton';
import router from 'next/router';
import Title from '@/components/Typography/Title';
import { LoginContainer, LoginWarp } from '@/components/Account/Login.styled';

export default function Login() {
  const HandleKakaoLogin = () => {
    const redirectUri = `${window.location.origin}/account/oauth/kakao`;
    router.push(
      `https://kauth.kakao.com/oauth/authorize?client_id=caeb5575d99036003c187adfadea9863&redirect_uri=${redirectUri}&response_type=code`
    );
  };

  const HandlegoogleLogin = () => {
    const redirectUri = `${window.location.origin}/account/oauth/google`;
    router.push(
      `https://accounts.google.com/o/oauth2/v2/auth?client_id=972594831157-fdfm8rq46vrb3tl81ds49o5978hs2ld0.apps.googleusercontent.com&redirect_uri=${redirectUri}&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile`
    );
  };

  const HandleHomepage = () => {
    router.push('/');
  };

  return (
    <LoginContainer>
      <LoginWarp>
        <br />
        <br />
        <Title textAlign="center" sizeType="2xl" color="blackColor90">
          소셜로그인
        </Title>
        <br />
        <br />
        <IconButton isRound className="kakao" onClick={HandleKakaoLogin}>
          <RiKakaoTalkFill className="icon" fill="#3B1A1F" size="30" />
          <span className="flex1"> 카카오로 시작하기 </span>
        </IconButton>
        <br />
        <IconButton isRound color="blackColor10" className="google" onClick={HandlegoogleLogin}>
          <FcGoogle className="icon" size="30" />
          <span className="flex1"> 구글로 시작하기 </span>
        </IconButton>
      </LoginWarp>
    </LoginContainer>
  );
}
