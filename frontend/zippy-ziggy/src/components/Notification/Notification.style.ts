import styled from 'styled-components';

export const Container = styled.div`
  width: 100%;
`;

export const Wrap = styled.div`
  padding: 48px 24px 48px 24px;
  margin: auto;
  display: flex;
  max-width: 480px;
  flex-direction: column;
  button {
    :hover {
      background-color: ${({ theme }) => theme.colors.linkColor};
      transform: translate(0px, -2px);
    }
  }
  .allButtonsContainer {
    button {
      margin: 8px 8px 8px 0px;
      background-color: ${({ theme }) => theme.colors.whiteColor70};
    }
  }

  ul {
    border-radius: 8px;
    cursor: pointer;
    li {
      border-top: 1px solid ${({ theme }) => theme.colors.blackColor05};
      border-left: 1px solid ${({ theme }) => theme.colors.blackColor05};
      border-right: 1px solid ${({ theme }) => theme.colors.blackColor05};
      padding: 16px;
      &.read {
        color: ${({ theme }) => theme.colors.blackColor30};
      }
      &.unread {
        background-color: ${({ theme }) => theme.colors.linkColor};
        color: ${({ theme }) => theme.colors.whiteColor};
        font-weight: 600;
      }
      .buttonContainer {
        margin-top: 8px;
        display: flex;
        justify-content: flex-end;
        button {
          margin-left: 8px;
          background-color: ${({ theme }) => theme.colors.whiteColor10};
        }
      }
    }
  }
  .moreBtn {
    border-radius: 0 0 8px 8px;
  }
`;
