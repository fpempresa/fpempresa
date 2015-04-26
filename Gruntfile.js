module.exports = function (grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        clean: ['.tmp'],
        useminPrepare: {
            html: 'web/**/*.html',
            options: {
                dest: 'web'
            }
        },
        concat: {
            options: {
                separator: ';'
            }
        },
        uglify: {

        },
        cssmin: {

        },
        rev: {

        },        
        usemin: {
            html: ['web/**/*.html'],
            options: {
                assetsDirs: ['web/**']
            }
        },
        


    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-rev');
    grunt.loadNpmTasks('grunt-usemin');


    grunt.registerTask('default', [
        'useminPrepare', 'concat', 'uglify','cssmin','usemin'
    ]);
};