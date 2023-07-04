import { media } from '@/styles/media';
import styled from 'styled-components';

export const ModifyContainer = styled.div`
  width: 100%;
  padding: 48px 16px 0px 16px;
  height: 100vh;
  background-color: ${({ theme: { colors } }) => colors.whiteColor100};
`;

export const ModifyWarp = styled.div`
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
`;
