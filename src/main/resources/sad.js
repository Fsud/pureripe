{
    var b = document.createElement('script');
    b.type = 'text/javascript';
    b.src = this.$('faptcha_server').value + '?action=reload&k=' + this.$('faptcha_public_key').value + '&c=' + this.$('faptcha_challenge_field').value;
    var a = document.getElementsByTagName('head');
    a = !a || 1 > a.length ? document.body : a[0];
    a.appendChild(b);
    if (this.audioOn) {
        document.getElementById('faptcha_audio_holder').innerHTML = document.getElementById('faptcha_audio_holder_back').innerHTML;
    }
}