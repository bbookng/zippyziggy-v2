import ProfileImage from '@/components/Image/ProfileImage';
import { ProfileContainer, ProfileHeaderContainer } from '@/components/Profile/Profile404.styled';
import Title from '@/components/Typography/Title';

export default function Index() {
  return (
    <ProfileContainer>
      <ProfileHeaderContainer>
        <ProfileImage src="/images/notfound.gif" alt="프로필이미지" size={128} />
        <Title sizeType="2xl">현재 사용자를 찾을 수 없습니다</Title>
      </ProfileHeaderContainer>
    </ProfileContainer>
  );
}
