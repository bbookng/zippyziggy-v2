import styled from 'styled-components';

export const ProfileContainer = styled.div`
  width: 100%;
  height: 100vh;
  background-color: ${({ theme: { colors } }) => colors.whiteColor100};
`;

export const ProfileHeaderContainer = styled.div`
  width: 100%;
  padding: 48px 48px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: ${({ theme: { colors } }) => colors.whiteColor100};
`;

export const ProfilePromptContainer = styled.div`
  width: 100%;
  padding: 48px 16px;
  background-color: ${({ theme: { colors } }) => colors.bgColor};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .promptBtns {
    display: flex;
    flex-flow: wrap;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 24px;
  }
`;

export const ProfileTalkContainer = styled.div`
  width: 100%;
  padding: 48px 16px;
  background-color: ${({ theme: { colors } }) => colors.navColor};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
