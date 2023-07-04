import { media } from '@/styles/media';
import styled from 'styled-components';

export const SignupContainer = styled.div`
  width: 100%;
  padding: 48px 16px 0px 16px;
  height: 100vh;
  background-color: ${({ theme: { colors } }) => colors.whiteColor100};
`;

export const SignupWarp = styled.div`
  max-width: 360px;
  margin: auto;

  .LogoImage {
    object-fit: contain;
    cursor: pointer;
    margin: auto;
    ${media.small`
      width: 100px;
      height: 48px;
    `}
  }

  .nickNameInput {
    width: 100%;
    height: 48px;
    border: 1px solid ${({ theme: { colors } }) => colors.blackColor05};
  }

  .legal {
    margin: 0 0 12px 0;
    padding: 16px;
    background-color: ${({ theme: { colors } }) => colors.bgColor};
  }
`;

export const WelcomeContainer = styled.div`
  width: 100%;
  height: 100vh;
  padding: 16px;
  background-color: ${({ theme: { colors } }) => colors.whiteColor100};
`;

export const WelcomeWarp = styled.div`
  max-width: 720px;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  position: absolute;
  display: flex;
  flex-direction: column;
  align-content: center;
  align-items: center;

  .kakao {
    background-color: #ffff16;
    color: #3b1a1f;
  }

  .google {
    background-color: ${({ theme: { colors } }) => colors.whiteColor};
    color: ${({ theme: { colors } }) => colors.blackColor};
    border: 1px solid ${({ theme: { colors } }) => colors.blackColor05};
  }

  .LogoImage {
    object-fit: contain;
    cursor: pointer;
    margin: auto;
    ${media.small`
      width: 100px;
      height: 48px;
    `}
  }
`;

export const LottieWrap = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .lottie {
    pointer-events: none;
    background-color: transparent;
    width: 600px;
  }
`;
