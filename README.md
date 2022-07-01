# 골목 대장 Spring Repository

[서버팀 노션 페이지](https://bronzed-boursin-3a7.notion.site/Server-Spring-795513f7687d4e999754f0694c4108ea)

### 협업 방법
1. 코드를 다운받을 local 위치에서 git clone하기 <br>
   `git clone https://github.com/UMC-Ringleader/ringLeaderServer.git`
2. 이름으로 새로운 브랜치 생성<br>
   `git checkout -b {닉네임}`
   Ex) `git checkout -b Royce`
3. 해당 브랜치에서 기능 구현하며 구현 단위 별로 커밋 하기
   Ex) `git commit -m "feat : 로그인 검증 구현"`
4. 개발 완료 후 해당 브랜치 push하여 PR 요청하기
   Ex) `git push origin Royce`

> 다시 개발을 시작할 땐, remote repo(github)의 master를 pull해야 합니다. 
> 
> Ex)
>
> `git checkout master` //master 브랜치로 이동
>
> `git pull origin master` //master 브랜치 업데이트 (변경사항 업데이트)
>
> `git checkout -b Royce` //다시 개별 브랜치로 이동 후,
> 
> `git merge master` //개발 브랜치에 변경사항 적용
> 
> 

