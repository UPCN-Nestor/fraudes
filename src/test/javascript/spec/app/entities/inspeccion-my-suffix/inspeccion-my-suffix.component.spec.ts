/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { InspeccionMySuffixComponent } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix.component';
import { InspeccionMySuffixService } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix.service';
import { InspeccionMySuffix } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix.model';

describe('Component Tests', () => {

    describe('InspeccionMySuffix Management Component', () => {
        let comp: InspeccionMySuffixComponent;
        let fixture: ComponentFixture<InspeccionMySuffixComponent>;
        let service: InspeccionMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InspeccionMySuffixComponent],
                providers: [
                    InspeccionMySuffixService
                ]
            })
            .overrideTemplate(InspeccionMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InspeccionMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InspeccionMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InspeccionMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.inspeccions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
