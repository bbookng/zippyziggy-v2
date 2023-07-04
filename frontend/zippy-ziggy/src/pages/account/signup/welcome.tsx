import Paragraph from '@/components/Typography/Paragraph';
import Title from '@/components/Typography/Title';
import router from 'next/router';
import lottieJson from '@/assets/lottieJson/congratulation-sparkle.json';
import Lottie from 'react-lottie-player';
import Link from 'next/dist/client/link';
import IconButton from '@/components/Button/IconButton';
import { FiLink2 } from 'react-icons/fi';
import { links } from '@/utils/links';
import { LottieWrap, WelcomeContainer, WelcomeWarp } from '@/components/Account/Signup.style';

export default function Index() {
  const { nickname } = router.query;

  const handleGptBtn = () => {
    router.push(links.downloadLink);
  };

  return (
    <WelcomeContainer>
      <LottieWrap>
        <Lottie className="lottie" loop animationData={lottieJson} play />
      </LottieWrap>
      <WelcomeWarp>
        <Title textAlign="center" margin="32px 0 0 0">
          {nickname}님 환영합니다!
        </Title>
        <Paragraph
          textAlign="center"
          margin="16px 0 0 0"
          color="linkColor"
          style={{ cursor: 'pointer' }}
        >
          <Link href="/prompts">유용한 프롬프트를 찾으러가기 →</Link>
        </Paragraph>

        <IconButton
          id="integrate"
          isRound
          display="inline-block"
          width="fit-content"
          color="linkColor"
          padding="0px 24px"
          margin="8px 0 4px 0 "
          onClick={handleGptBtn}
        >
          <FiLink2 className="icon" size="20" style={{ marginLeft: '8px' }} />
          <span className="flex1" style={{ marginLeft: '8px', marginRight: '12px' }}>
            GPT 확장 다운로드
          </span>
        </IconButton>
      </WelcomeWarp>
    </WelcomeContainer>
  );
}
