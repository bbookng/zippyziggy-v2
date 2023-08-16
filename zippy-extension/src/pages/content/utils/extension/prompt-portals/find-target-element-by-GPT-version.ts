// 타겟 엘리먼트에 루트 래퍼를 추가하는 함수
const addRootWrapperToTargetElement = ($target: HTMLElement): HTMLElement => {
  // div 엘리먼트를 생성하고 스타일 클래스를 할당
  const rootWrapper = document.createElement('div');
  rootWrapper.className = 'px-2 py-10 relative w-full flex flex-col h-full';

  // 타겟을 부모 엘리먼트로 변경
  const $parentOfTarget = $target.parentElement;

  // 타겟의 부모에서 두 번째 div 엘리먼트를 찾아서 숨김
  const $secondDiv = $parentOfTarget.querySelector('div:nth-of-type(2)') as HTMLElement;
  $secondDiv.style.display = 'none';

  // 타겟에 루트 래퍼를 자식으로 추가
  $parentOfTarget.appendChild(rootWrapper);

  // 루트 래퍼 반환
  return rootWrapper;
};

// 타겟 엘리먼트를 찾는 함수, 셀렉터 문자열을 매개변수로 받음
const findTarget = ($parent: Element, selector: string): HTMLElement | null => {
  // 주어진 셀렉터로 타겟 엘리먼트를 찾음
  const $target = $parent.querySelector(selector) as HTMLElement;

  // 타겟이 존재하면 루트 래퍼를 추가, 그렇지 않으면 null 반환
  return $target ? addRootWrapperToTargetElement($target.parentElement) : null;
};

// GPT 버전에 따라 대상 요소를 찾아 반환하는 함수
export const findTargetElementByGPTVersion = ($parent: Element, isPlus: boolean) => {
  // isPlus 값에 따라 적절한 셀렉터를 사용하여 타겟을 찾음
  return isPlus
    ? findTarget($parent, 'div.px-2.w-full.flex.flex-col.py-2.md\\:py-6.sticky.top-0')
    : findTarget($parent, 'div.align-center.flex.h-full');
};
