import { media } from '@/styles/media';
import styled from 'styled-components';

export const LoginContainer = styled.div`
  width: 100%;
  height: 100vh;
  padding: 16px;
  background-color: ${({ theme: { colors } }) => colors.whiteColor100};
`;

export const LoginWarp = styled.div`
  max-width: 300px;
  margin: auto;

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
