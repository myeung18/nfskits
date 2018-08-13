login to oc

select the oc project


oc create -f my_iu_pv.yaml
oc create -f pvc-iu.yaml
oc volume dc/app-cli --add --type=persistentVolumeClaim --claim-name=pvc-my-iu --mount-path=/home/myeung/app-root