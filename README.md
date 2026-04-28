# AI-Body-Rehabilitation
A portable non-contact AI auxiliary diagnosis method and system for rehabilitation training of upper and lower limbs after orthopedic surgery based on BlazePose human key point recognition

<img width="668" height="1019" alt="image" src="https://github.com/user-attachments/assets/67182ca2-c6ca-4012-9a13-2ecd931fbf28" />


<img width="2560" height="1600" alt="1" src="https://github.com/user-attachments/assets/38c7778e-2511-49ab-a11a-a2f4446be8e7" />

<img width="2560" height="1600" alt="2" src="https://github.com/user-attachments/assets/d65d50f6-a603-4522-b5e4-ff95fbffc510" />

<img width="2560" height="1600" alt="3" src="https://github.com/user-attachments/assets/e518eb6a-1767-4cbd-8b2e-47a6e8c2ae58" />

<img width="2560" height="1600" alt="4" src="https://github.com/user-attachments/assets/48bb36a9-df24-4f24-869b-c447f1b04d16" />

<img width="2560" height="1600" alt="5" src="https://github.com/user-attachments/assets/ac5ac60a-1f31-42d2-9ad1-461e1d3903f0" />


Operating environment:

Linux version 4.15.0-194-generic (buildd@lcy02-amd64-052) (gcc version 7.5.0 (Ubuntu 7.5.0-3ubuntu1~18.04)) #205-Ubuntu SMP Fri Sep 16 19:49:27 UTC 2022

build guide:

cai@ubuntu:~$ cd mediapipe

cai@ubuntu:~/mediapipe$ bazel build -c opt --config=android_arm64 mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare:AI-HealthCare

Starting local Bazel server and connecting to it...
... still trying to connect to local Bazel server after 10 seconds ...
... still trying to connect to local Bazel server after 20 seconds ...
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_absl' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_benchmark' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'pybind11_bazel' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_protobuf' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_googletest' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_github_gflags_gflags' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'build_bazel_rules_apple' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'build_bazel_rules_swift' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'build_bazel_apple_support' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'xctestrunner' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'pybind11' because it already exists.
WARNING: /home/cai/mediapipe/mediapipe/framework/tool/BUILD:184:24: in cc_library rule //mediapipe/framework/tool:field_data_cc_proto: target '//mediapipe/framework/tool:field_data_cc_proto' depends on deprecated target '@com_google_protobuf//:cc_wkt_protos': Only for backward compatibility. Do not use.
WARNING: /home/cai/mediapipe/mediapipe/framework/BUILD:54:24: in cc_library rule //mediapipe/framework:calculator_cc_proto: target '//mediapipe/framework:calculator_cc_proto' depends on deprecated target '@com_google_protobuf//:cc_wkt_protos': Only for backward compatibility. Do not use.
INFO: Analyzed target //mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare:AI-HealthCare (198 packages loaded, 14647 targets configured).
INFO: Found 1 target...
Target //mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare:AI-HealthCare up-to-date:
  bazel-bin/mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare/AI-HealthCare_deploy.jar
  bazel-bin/mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare/AI-HealthCare_unsigned.apk
  bazel-bin/mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare/AI-HealthCare.apk
INFO: Elapsed time: 547.622s, Critical Path: 294.79s
INFO: 2 processes: 1 internal, 1 linux-sandbox.
INFO: Build completed successfully, 2 total actions

cai@ubuntu:~/mediapipe$ 

build guide end;
