import { createGlobalStyle } from 'styled-components';
import normalize from 'styled-normalize';
import { media } from './media';

const GlobalStyle = createGlobalStyle`
  ${normalize}

  *{
    transition: background-color 0.3s ease-in;
  }
  
  html {
    background-color: ${({ theme }) => theme?.colors?.bgColor};
    color: ${({ theme }) => theme?.colors?.blackColor90};
    /* min-width: var(--breakpoints-desktop); */
    
    ${media.small`
    min-width: unset;
    width: 100%;
    `}
  }

  body {
  }

  a {
    color: inherit;
    text-decoration: none;
  }

  button,
  input,
  optgroup,
  select,
  textarea {
    box-shadow: ${({ theme }) => theme.shadows.boxShadowLarge};
    background-color: ${({ theme }) => theme.colors.whiteColor70};
  }

  ::selection {
    background-color: ${({ theme }) => theme.colors.blackColor50};
    color: ${({ theme }) => theme.colors.blackColor90}
  }

  input::placeholder,
  textarea::placeholder {
    color: ${({ theme }) => theme.colors.blackColor50}
  }
`;

export default GlobalStyle;