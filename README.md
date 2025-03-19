# 😵‍💫 Opensearch-Hangul-Plugin

본 프로젝트는 `Java Cafe`에서 개발한 플러그인을 `Fork` 하여 `OpenSearch Plugin`으로 만들었음을 밝힙니다.

`OpenSearch 2.19.0` 버전에 대응되는 플러그인입니다.

[원본 플러그인 링크](https://github.com/javacafe-project/elasticsearch-plugin)

### 🔧 제공 기능

- 한 ↔ 영 변환 검색
- 자모 분해(한글 오타 검증)
- 초성 검색

### 💾 설치 방법

본 플러그인을 설치하기 전 `JDK 21`는 설치가 되어있어야 합니다.

`OpenSearch`가 구축되어 있다면, `OpenSearch` 설치 부분은 건너 뛰셔도 무방합니다.

😎 **Opensearch 설치**

`OpenSearch`는 `Docker-Compose` 파일로 제공되며, 사전에 `Docker-Compose` 설치가 되어있어야 합니다.

- 프로젝트 루트에서 `docker` 폴더로 이동합니다.

```bash
$ cd docker
```
<br>

- `opensearch-demo.yml` 파일을 실행합니다.

```bash
$ docker-compose -f opensearch-demo.yml up -d
```
<br>

😁 **Plugin Build**

- `프로젝트 루트`에서 아래와 같은 `명령어`를 `실행`합니다.

```bash
$ ./gradlew clean build
```
<br>

- `빌드`가 완료되면 아래 `경로`를 `확인`합니다.
```bash
$ ls build/distributions
```
<br>

![image](https://github.com/user-attachments/assets/c58de74e-fbf4-4897-8095-6478482846dc)
<br>

🚀 **플러그인 설치**

`Plugin` 설치를 위해서 `OpenSearch` 설치 경로로 이동합니다.

본 문서에서는 프로젝트에 포함된 `docker-compose` 파일로 `OpenSearch`를 설치했다는 가정아래 가이드를 작성했습니다.

- `opensearch-custom-plugin-2.19.0.zip`  파일을 복사합니다.

```bash
// 현재 파일을 opensearch container 내부로 이동
$ docker cp opensearch-custom-plugin-2.19.0.zip opensearch:/usr/share/opensearch/bin
```
<br>

- `Docker Container`에 접속하여 플러그인을 설치합니다.

```bash
$ docker exec -it opensearch bash

// container 내에서 bin 폴더로 이동
$ cd bin

// plugin 설치
$ opensearch-plugin install file:///usr/share/opensearch/bin/opensearch-custom-plugin-2.19.0.zip
```
<br>

- 설치 후 `OpenSearch Container`를 재실행합니다.

```bash
$ docker restart opensearch
```
<br>

- `플러그인 정상 설치` 여부를 확인합니다.

```bash
$ curl -X GET "http://localhost:9200/_cat/plugins"
```
<br>

![image](https://github.com/user-attachments/assets/a7f23956-56f6-4ac1-82b3-a64dc5b8bd26)
<br>


정상적으로 `플러그인 목록`에서 조회되면 설치 완료입니다.

### 한 → 영 변환 오타 검색

`iphone` 이라는 단어를 `ㅑㅔㅙㅜㄷ`으로 검색했을때 오타를 보정해 검색 결과가 나올 수 있도록 제공합니다.

`이중 모음`에도 대응되어 있어, `많은 케이스`에 대해 커버가 가능합니다.

```json
// index 생성
PUT convert_kor_to_eng
{
  "settings": {
    "analysis": {
      "analyzer": {
        "kor_to_eng_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "convert_kor_to_eng_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "text": {
        "type": "text",
        "copy_to": "kor_to_eng"
      },
      "kor_to_eng": {
        "type": "text",
        "search_analyzer": "kor_to_eng_analyzer"
      }
    }
  }
}

// Sample 데이터 저장
POST convert_kor_to_eng/_doc
{
  "text": "iphone"
}

// 조회 쿼리 전송
GET convert_kor_to_eng/_search
{
  "query": {
    "match": {
      "kor_to_eng": "ㅑㅔㅙㅜㄷ"
    }
  }
}

// 조회 결과
{
  "took": 1,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 1,
      "relation": "eq"
    },
    "max_score": 0.2876821,
    "hits": [
      {
        "_index": "convert_kor_to_eng",
        "_id": "zbP2q5UBRFSSemJYcoQS",
        "_score": 0.2876821,
        "_source": {
          "text": "iphone"
        }
      }
    ]
  }
}
```

### 영 → 한 변환 오타 검색

`삼성전자`라는 단어를 `tkawjdwjswk`로 검색했을때 오타를 보정해 검색 결과가 나올 수 있도록 제공합니다.

```json
// 인덱스 생성
PUT convert_eng_to_kor
{
  "settings": {
    "analysis": {
      "analyzer": {
        "eng_to_kor_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "convert_eng_to_kor_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "text": {
        "type": "text",
        "copy_to": "eng_to_kor"
      },
      
      "eng_to_kor": {
        "type": "text",
        "search_analyzer": "eng_to_kor_analyzer"
      }
    }
  }
}

// Sample 데이터 저장
POST convert_eng_to_kor/_doc
{
  "text": "삼성전자"
}

// 조회 쿼리 전송
GET convert_eng_to_kor/_search
{
  "query": {
    "match": {
      "eng_to_kor": "tkatjdwjswk"
    }
  }
}

// 조회 결과
{
  "took": 3,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 1,
      "relation": "eq"
    },
    "max_score": 0.2876821,
    "hits": [
      {
        "_index": "convert_eng_to_kor",
        "_id": "zLP0q5UBRFSSemJYv4SP",
        "_score": 0.2876821,
        "_source": {
          "text": "삼성전자"
        }
      }
    ]
  }
}
```

### 초성 변환 검색

`아마존`이라는 단어를 `ㅇㅁㅈ`으로 검색했을때 검색어를 초성 변환 후 검색 결과가 나올 수 있도록 제공합니다.

```json
// 인덱스 생성
PUT convert_chosung
{
  "settings": {
    "analysis": {
      "analyzer": {
        "chosung_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "kor_chosung_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "text": {
        "type": "text",
        "copy_to": "chosung"
      },
      
      "chosung": {
        "type": "text",
        "analyzer": "chosung_analyzer"
      }
    }
  }
}

// Sample 데이터 저장
POST convert_chosung/_doc
{
  "text": "아마존"
}

// 조회 쿼리 전송
GET convert_chosung/_search
{
  "query": {
    "match": {
      "chosung": "ㅇㅁㅈ"
    }
  }
}

// 조회 결과
{
  "took": 3,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 1,
      "relation": "eq"
    },
    "max_score": 0.2876821,
    "hits": [
      {
        "_index": "convert_chosung",
        "_id": "z7MlrJUBRFSSemJYfYRH",
        "_score": 0.2876821,
        "_source": {
          "text": "아마존"
        }
      }
    ]
  }
}
```

### 한글 자모 분석 필터 / 한글 오타 체크 필터

`한글 자모 분석 필터`와 `한글 오타 체크 필터`는 동일한 기능을 제공하므로 묶어서 설명합니다.

`한글`로 구성된 단어를 `자모 단위`로 분리해주는 `필터`를 `제공`합니다.

ex) `아마존` → ㅇㅏㅁㅏㅈㅗㄴ

ex) `삼성전자` → ㅅㅏㅁㅅㅓㅇㅈㅓㄴㅈㅏ

```json
// 인덱스 생성
PUT decompose_jamo
{
  "settings": {
    "analysis": {
      "analyzer": {
        "kor_jamo_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "kor_jamo_filter"
          ]
        }
      }
    }
  }
}

// 특정 키워드 Analyze 요청 전송
POST decompose_jamo/_analyze
{
  "analyzer": "kor_jamo_analyzer",
  "text": "아마존"
}

// Analyze 결과
{
  "tokens": [
    {
      "token": "ㅇㅏㅁㅏㅈㅗㄴ",
      "start_offset": 0,
      "end_offset": 3,
      "type": "<HANGUL>",
      "position": 0
    }
  ]
}
```
